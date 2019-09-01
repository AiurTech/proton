lazy val protonServer = Project(id = "proton", base = file(".")).enablePlugins(JavaAppPackaging)

organization := "uk.co.aiur"
name := "proton"
version := "0.4.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  // "de.heikoseeberger" %% "akka-http-circe" % "1.27.0",
  "ch.qos.logback"    % "logback-classic"      % "1.2.3",
  "org.scalatest"     %% "scalatest"           % "3.0.8" % "test",
  "org.mockito"       % "mockito-core"         % "3.0.0",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.23" % "test",
  "com.typesafe.akka" %% "akka-http-testkit"   % "10.1.9" % "test"
)

maintainer := "cto+scala@aiur.co.uk"

mappings in (Compile, packageDoc) := Seq() //disable documentation generation

scalacOptions ++= List(
  "-encoding",
  "utf8",
  "-explaintypes",
  "-feature",
  "-deprecation",
  "-unchecked",
  "-Xlint",
  "-Ypartial-unification",
  "-Yrangepos",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
)
