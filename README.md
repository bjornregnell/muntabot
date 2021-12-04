# muntabot
![Build Status](https://github.com/bjornregnell/muntabot/actions/workflows/ci.yml/badge.svg)

This single-page client-only web app is available for execution in your browser here:

https://cs.lth.se/pgk/muntabot


Developed using fantastic [Scala 3](https://scala-lang.org/) and [Scala JS](https://www.scala-js.org/doc/tutorial/basic/). Built using [`sbt`](https://www.scala-sbt.org/) and [Node.js](https://nodejs.org).

# How to develop

* Prerequisites: [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), [Node.js](https://nodejs.org/en/download/)

* Clone or download this repo on your machine.

* While hacking type `~fastLinkJS` in `sbt` and open/reload the `dev/index-dev.html` file in your browser

* To avoid having to reload the page upon every change the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) can be used to do this automatically. Install the extension in vscode and right-click `dev/index-dev.html` and click `Open with Live Server` after running `~fastLinkJS` in `sbt`.

# How to deploy 

When ready for production use this script:

```bash
sbt fullLinkJS
cp target/scala-3.1.0/muntabot-opt/main.js public/main.js

```

and upload the contents of the `public` folder to your webserver including the files `main.js`, `index.html` and `style.css`.

To test before uploading to production you can install the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) and right-click `public/index.html` and click `Open with Live Server`.