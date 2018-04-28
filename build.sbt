import Dependencies._

lazy val root = (project in file(".")).
  settings( 
    organization := "com.davoscollective",
    version      := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.5",
    name := "scala-string-compression",
    //libraryDependencies += scalaTest % Test
    libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
  )
