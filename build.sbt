import Dependencies._

lazy val sample = (project in file("."))
  .enablePlugins(
    JavaServerAppPackaging,
    JavaAppPackaging,
    DockerPlugin,
    AshScriptPlugin
  )
  .settings(version := "1.0.22")
  .settings(scalaVersion := "2.12.8")
  .settings(fork in run := true)
  .settings(mainClass in assembly := Some("com.akehoyayoi.Main"))
  .settings(mainClass in Compile := Some("com.akehoyayoi.Main"))
  .settings(libraryDependencies ++= deps)