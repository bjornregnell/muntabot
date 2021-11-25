# muntabot

This single-page client-only web app is available for execution in your browser here: 

https://cs.lth.se/pgk/muntabot


Developed using fantastic [Scala 3](https://scala-lang.org/) and [Scala JS](https://www.scala-js.org/doc/tutorial/basic/). It is built using [`sbt`](https://www.scala-sbt.org/).

# How to build

* Prerequisites: [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), [Node.js](https://nodejs.org/en/download/)

* Clone or download this repo on your machine.

* Inside `sbt` in terminal:
    * While hacking type `~fastLinkJS` in `sbt` and open/reload the `index-dev.html` file in your browser
    * When ready for production type `fullLinkJS` and upload the files `index.html` and `target/scala-3.0.0/hello-scala-js-opt/main.js` to your web server.