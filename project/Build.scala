import feh.util.TestReportsCopy
import sbt.Keys._
import sbt._
import sbtunidoc.Plugin._

object  Build extends sbt.Build {

  val ScalaVersion = "2.11.5"
  val Version = "0.5-SNAPSHOT"

  import Build.Dependencies._

  val buildSettings = Defaults.coreDefaultSettings ++ Seq (
    organization  := "feh.phtpe",
    version       := Version,
    scalaVersion  := ScalaVersion,
    resolvers     += Resolvers.fehu,
    scalacOptions in (Compile, doc) ++= Seq("-diagrams", "-diagrams-max-classes", "50", "-diagrams-max-implicits", "20")
  )

  lazy val testSettings = TestReportsCopy.settings ++ Seq(
    libraryDependencies += "org.specs2" %% "specs2" % "2.4.15" % "test",
    resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",

    TestReportsCopy.copyTestReportsDir <<= baseDirectory(base => Some(base / "test-reports")),
    TestReportsCopy.autoAddReportsToGit := true
  )

  // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

  object Resolvers{
    lazy val fehu = "Fehu's github repo" at "http://fehu.github.io/repo"
  }

  object Dependencies{
    object scala{
      lazy val libAll = "org.scala-lang" % "scala-library-all" % ScalaVersion // 2.11.x
    }

    object feh{
      lazy val util = "feh.util" %% "util" % "1.0.6"
    }
  }

  // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = buildSettings ++ unidocSettings ++ Seq(
      name := "root",
      publishArtifact := false
    )
  ).aggregate(phtpe, vectors)

  lazy val phtpe = Project("phtpe", file("phtpe"),
    settings = buildSettings ++ testSettings ++ Seq(
      name := "typing",
      libraryDependencies ++= Seq(scala.libAll, feh.util),
      initialCommands +=
        s"""import feh.phtpe._
           |import short._
           |import Prefixes._
         """.stripMargin
    )
  )

  lazy val vectors = Project("vectors", file("vectors"),
    settings = buildSettings ++ testSettings ++ Seq(
      initialCommands +=
        s"""import feh.phtpe._
           |import short._
           |import Prefixes._
           |import vectors._
         """.stripMargin
    )
  ) dependsOn phtpe

}
