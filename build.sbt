organization := "uk.co.aiur"
name := "aiur-proton"
version := "0.4"

scalaVersion := "2.12.8"

scalacOptions ++= List(
  "-encoding", "utf8",
  "-feature", 
  "-deprecation", 
  "-unchecked", 
  "-Xlint",
  "-Ypartial-unification",
  "-Yrangepos",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
)

addCompilerPlugin(scalafixSemanticdb)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "org.scalatest"     %% "scalatest"   % "3.0.5" % "test"
)

maintainer := "cto+scala@aiur.co.uk"

lazy val protoinServer = Project(id = "proton", base = file(".")).
	enablePlugins(JavaAppPackaging)
