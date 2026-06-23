enablePlugins(ScalaJSPlugin)

name := "muntabot"
scalaVersion := "3.8.4"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.1")

// Silence Scala 3 [E222]: our top-level `val`s live in hyphenated files
// (data-sv.scala, data-en.scala, *-GENERATED.scala), so the compiler's synthetic
// `<file>$package` object name needs classpath encoding. Harmless here (it works).
// Future cleanup (not done now): rename the files hyphen-free, or wrap the defs in an
// object — see the work repo's notes/plan.md. Silenced for now to keep builds clean.
scalacOptions += "-Wconf:msg=will be encoded on the classpath:silent"

