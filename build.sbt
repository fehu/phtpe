
lazy val commonSettings = Seq(
  organization := "feh.phtpe",
  crossScalaVersions := Seq("2.11.5"),
  scalaVersion := crossScalaVersions.value.head,
  scalacOptions in (Compile, doc) ++= Seq("-diagrams", "-diagrams-max-classes", "50", "-diagrams-max-implicits", "20"),
  resolvers += "Fehu's github repo" at "http://fehu.github.io/repo"
)

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(sbtunidoc.Plugin.unidocSettings: _*)
  .settings(publishArtifact := false)
  .aggregate(typing, vectors)

lazy val typing = project.in(file("phtpe"))
  .settings(commonSettings: _*)
  .settings(testSettings: _*)

lazy val vectors = project.in(file("vectors"))
  .settings(commonSettings: _*)
  .settings(testSettings: _*)
  .dependsOn(typing)

// Test Settings

import feh.util.TestReportsCopy

lazy val testSettings = TestReportsCopy.settings ++ Seq(
  libraryDependencies += "org.specs2" %% "specs2" % "2.4.15" % "test",
  resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",

  TestReportsCopy.copyTestReportsDir := Some(file("./test-reports")),
  TestReportsCopy.autoAddReportsToGit := true
)
