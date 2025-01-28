enablePlugins(ScalaJSPlugin)

name := "muntabot"
scalaVersion := "3.6.3"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

//https://www.scala-js.org/doc/tutorial/scalajs-vite.html#introducing-scalajs
libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.0")//.cross(CrossVersion.for3Use2_13)

