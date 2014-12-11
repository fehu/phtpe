import sbt._
import Keys._
import FileUtils._
import java.lang.{ProcessBuilder => JProcessBuilder}

object TestSettings {
  def get = settings

  lazy val settings = Seq(
    libraryDependencies += Build.Dependencies.Tests.specs2,
    testOptions in Test += Tests.Argument("markdown", "console" /*"html"*/),
    reportsDir := file("target/specs2-reports"),
    cleanTestReports <<= cleanTestReportsTask,
    copyTestReports <<= copyGeneratedTestReportsTask,
    copyTestReports <<= copyTestReports.dependsOn(test in Test),
    //    addReportsToGit <<= addReportsToGitTask,
    setAutoAddReportsToGit,
    setAutoCleanTestReports,
    // defaults
    autoAddReportsToGit := false,
    autoCleanExports := false
  )

  val copyTestReports = TaskKey[Seq[File]]("copy-test-reports")
  val cleanTestReports = TaskKey[Unit]("clean-test-reports")
  //  val addReportsToGit = TaskKey[Unit]("add-test-reports-to-git")

  val copyTestReportsDir = SettingKey[Option[File]]("test-reports-copy-dir")
  val reportsDir = SettingKey[File]("specs-reports-dir")
  val autoCleanExports = SettingKey[Boolean]("clean-test-reports-on-clean-auto")
  val autoAddReportsToGit = SettingKey[Boolean]("add-test-reports-to-git-on-copy-auto")
  val gitRoot = SettingKey[File]("add-test-reports-to-git-on-copy-auto")

  val copyGeneratedTestReportsTask = (copyTestReportsDir, definedTestNames in Test, streams, reportsDir, state) map {
    (exportOpt, names, s, report, state) =>
      exportOpt map {
        exportDist =>
          val log = s.log
          if (names.nonEmpty) {
            if (!exportDist.exists()) exportDist.mkdir()
            log.info(s"exporting specs generated markdown for project ${Project.current(state).project}:")
            log.info(names.mkString("\t", "\n", ""))

            val dest = report ** "*.md" pair rebase(report, exportDist) rename( """feh\.util\.(.*)""".r, "$1")
            log.debug("copyGeneratedTestReportsTask: dest: " + dest)

            for {
              (from, to) <- dest
            } yield {
              Sync.copy(from, to)
              to
            }
          }
          else Nil
      } getOrElse Nil
  }

  val cleanTestReportsTask = (copyTestReportsDir, streams) map { (exportOpt, st) =>
    exportOpt foreach {
      export =>
        val log = st.log
        if (export.exists()) {
          log.info("Cleaning specs generated markdown exports in " + export)
          for {
            report <- (export ** "*.md").get
          } report.delete()
          if (!export.delete())
            log.warn("Markdown exports dir contains files different from .md")
        }
    }
  }

  def addReportsToGit(reports: Seq[File], baseDir: File, log: Logger) =
    if (reports.nonEmpty) {
          log.debug("addReportsToGit: reports: " + reports)
          log.debug("addReportsToGit: reports exist: " + reports.map(_.exists()))
      log.info("Adding generated test reports to git repo")
      val paths = reports.map(_.relativeTo(baseDir).get)
      val cmd = List("git", "add") ++ paths.map(_.getPath)
          log.debug("addReportsToGit: git command: " + cmd.toSeq)
          log.debug("addReportsToGit: base dir: " + baseDir)
      val pb = new JProcessBuilder(cmd: _*)
      val code = pb.directory(baseDir).redirectErrorStream(true).start().waitFor()
      if(code != 0) log.warn("Failed to add test reports to git repo")
    }

  val setAutoCleanTestReports = clean := {
    clean.value
    autoCleanExports.map{
      set =>
        if(set) cleanTestReports.map(identity) else {}
    }
  }

  val setAutoAddReportsToGit = copyTestReports <<= (copyTestReports, autoAddReportsToGit, streams, baseDirectory) map {
    (copied, set, st, baseDir) =>
      if (set) addReportsToGit(copied.get, baseDir, st.log)
      copied
  }

}
