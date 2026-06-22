enablePlugins(ScalaJSPlugin)

name := "muntabot"
scalaVersion := "3.8.4"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.1")

