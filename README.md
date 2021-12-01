# muntabot

This single-page client-only web app is available for execution in your browser here: 

https://cs.lth.se/pgk/muntabot


Developed using fantastic [Scala 3](https://scala-lang.org/) and [Scala JS](https://www.scala-js.org/doc/tutorial/basic/). It is built using [`sbt`](https://www.scala-sbt.org/).

# How to develop

* Prerequisites: [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), [Node.js](https://nodejs.org/en/download/)

* Clone or download this repo on your machine.

* While hacking type `~fastLinkJS` in `sbt` and open/reload the `public/*.html` file in your browser

# How to deploy 

When ready for production use this script:

```bash
sbt --client fullLinkJS
rm public/main.js
mv target/scala-3.1.0/muntabot-opt/main.js public/main.js

```

and upload the contents to of /public to your webserver.