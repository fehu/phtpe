name := "typing"

version := "0.5-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "feh.util" %% "util" % "1.0.9-SNAPSHOT"
)

initialCommands +=
  """import feh.phtpe._
    |import short._
    |import Prefixes._
  """.stripMargin
