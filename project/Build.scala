import org.sbtidea.SbtIdeaPlugin._
import sbt.Keys._
import sbt._
import sbtunidoc.Plugin._

object  Build extends sbt.Build {

  val ScalaVersion = "2.11.4"
  val Version = "0.3-SNAPSHOT"

  import Build.Dependencies._

  val buildSettings = Defaults.coreDefaultSettings ++ Seq (
    organization  := "feh.phtpe",
    version       := Version,
    scalaVersion  := ScalaVersion,
//    scalacOptions ++= Seq("-explaintypes"),
//    scalacOptions ++= Seq("-deprecation"),
//    scalacOptions ++= Seq("-Ydebug"),
//    scalacOptions ++= Seq("-Xlog-free-terms"),
//    scalacOptions ++= Seq("-Ymacro-debug-lite"),
    scalacOptions in (Compile, doc) ++= Seq("-diagrams", "-diagrams-max-classes", "50", "-diagrams-max-implicits", "20")
//     mainClass in Compile := Some("")
  )

  lazy val testSettings = TestSettings.get ++ Seq(
    TestSettings.copyTestReportsDir <<= baseDirectory(base => Some(base / "test-reports")),
    TestSettings.autoAddReportsToGit := true,
    resolvers += Resolvers.Release.scalaz
  )

  // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

  object Resolvers{
    object Release{
      lazy val sonatype = "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
      lazy val spray = "spray" at "http://repo.spray.io/"
      lazy val scalaz = "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
    }

    object Snapshot{
      lazy val sonatype = "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
      lazy val eulergui = "eulergui" at "http://eulergui.sourceforge.net/maven2"
      lazy val spray = "spray nightlies repo" at "http://nightlies.spray.io"
    }

  }

  object Dependencies{
    def AkkaVersion = "2.3.3"
    lazy val akka = "com.typesafe.akka" %% "akka-actor" % AkkaVersion
    lazy val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
    lazy val slf4j = "org.slf4j" % "slf4j-log4j12" % "1.7.7"

    object scala{
      lazy val compiler = "org.scala-lang" % "scala-compiler" % ScalaVersion
      lazy val swing = "org.scala-lang" % "scala-swing" % ScalaVersion
      lazy val reflectApi = "org.scala-lang" % "scala-reflect" % ScalaVersion
      lazy val libAll = "org.scala-lang" % "scala-library-all" % ScalaVersion // 2.11.x
    }

    object typesafe{
      lazy val config = "com.typesafe" % "config" % "1.2.1"
    }

    object Apache{
      lazy val ioCommons = "commons-io" % "commons-io" % "2.4"
    }

    object spray{
      lazy val json = "io.spray" %%  "spray-json" % "1.2.6"
      lazy val can = "io.spray" %% "spray-can" % "1.3.1"
      lazy val websocket= "com.wandoulabs.akka" %% "spray-websocket" % "0.1.3"
    }

    object Tests{
//      lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.10.1" % "test"
      lazy val specs2 = "org.specs2" %% "specs2" % "2.4.15" % "test"
    }

    object feh{
      lazy val util = ProjectRef( uri("git://github.com/fehu/util.git"), "util")
    }

    object js{
      lazy val jquery = "org.webjars" % "jquery" % "2.1.1"
      lazy val bootstrap = "org.webjars" % "bootstrap" % "3.2.0"
    }

    lazy val GitDependencies = feh.util :: Nil //feh.utils.compiler
  }

  // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = buildSettings ++ unidocSettings ++ Seq(
      name := "root"
    )
  ).settings(ideaExcludeFolders := ".idea" :: ".idea_modules" :: Nil)
   .aggregate(GitDependencies: _*)
   .aggregate(phtpe, vectors)

  lazy val phtpe = Project("phtpe", file("phtpe"),
    settings = buildSettings ++ testSettings ++ Seq(
      name := "typing",
      libraryDependencies ++= Seq(scala.libAll),
      initialCommands +=
        s"""import feh.phtpe._
           |import short._
         """.stripMargin
    )
  ) dependsOn feh.util

  lazy val vectors = Project("vectors", file("vectors"),
    settings = buildSettings ++ testSettings ++ Seq(
      initialCommands +=
        s"""import feh.phtpe._
           |import short._
           |import vectors._
         """.stripMargin
    )
  ) dependsOn phtpe

}
