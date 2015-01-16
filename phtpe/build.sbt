name := "typing"

version := "0.5-SNAPSHOT"

resolvers += "Fehu's github repo" at "http://fehu.github.io/repo"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "feh.util" %% "util" % "1.0.7"
)

initialCommands +=
  """import feh.phtpe._
    |import short._
    |import Prefixes._
  """.stripMargin