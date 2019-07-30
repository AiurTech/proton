organization := "uk.co.aiur"
name := "aiur-proton"
version := "0.4"

scalaVersion := "2.12.8"

scalacOptions ++= List("-feature", "-deprecation", "-unchecked", "-Xlint")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "org.scalatest"     %% "scalatest"   % "3.0.5" % "test"
)
