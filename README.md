# muntabot

This client-only web app is available for execution in your browser here: 

https://cs.lth.se/pgk/muntabot


Developed using fantastic [Scala 3](https://scala-lang.org/) and [Scala JS](https://www.scala-js.org/doc/tutorial/basic/). It is built using [`sbt`](https://www.scala-sbt.org/).

# How to develop

* Prerequisites: [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), [Node.js](https://nodejs.org/en/download/)

* Clone or download this repo on your machine.

* While hacking type `~fastLinkJS` in `sbt` and open/reload the `dev/index-dev.html` file in your browser

* To avoid having to reload the page upon every change the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) can be used to do this automatically. Install the extension in vscode and right-click `dev/index-dev.html` and click `Open with Live Server`

# How to deploy 

When ready for production use this script:

```bash
sbt --client fullLinkJS
rm public/main.js
mv target/scala-3.1.0/muntabot-opt/main.js public/main.js

```

and upload the contents to of /public to your webserver.

To test before uploading to production you can install the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) and right-click `public/index.html` and click `Open with Live Server`.