# muntabot
![Build Status](https://github.com/bjornregnell/muntabot/actions/workflows/main.yml/badge.svg)

This single-page client-only web app is available for execution in your browser here:

https://cs.lth.se/pgk/muntabot


Developed using fantastic [Scala 3](https://scala-lang.org/) and [Scala JS](https://www.scala-js.org/doc/tutorial/basic/). Built using [`sbt`](https://www.scala-sbt.org/) and [`nodejs`](https://nodejs.org).

# How to develop

* Prerequisites: [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), [Node.js](https://nodejs.org/en/download/)

* Clone or download this repo on your machine.

* The build creates `main.js` that is consumed by `index.html`, which in turn needs the `style.css` file for formatting.

* While developing type `~fastLinkJS` in `sbt` and open/reload the `dev/index-dev.html` file in your browser.

* If you are using a sand-boxed browser (e.g. Firefox installed as a snap) then local files are opened as something similar to `file:///run/user/1000/doc/ad1d72e5/index.html` which means that the index.html-page cannot access the accompanying local files main.js and style.css so you need to explicitly type the correct path in the URL field using something similar to  `file:///home/user/project/dev/index.html#muntabot` where you change the user and project in the path to where the files are located. Note the trippel slashes `///`

* To avoid having to reload the page upon every change the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) can be used to do this automatically. Install the extension in vscode and right-click `dev/index-dev.html` and click `Open with Live Server` after running `~fastLinkJS` in `sbt`. Don't forget to check that `dev/index-dev.html` points to the correct subdir of `target/scala-3.x.y/` ...

# How to deploy 

## Bump versions

* When bumping versions: DON'T FORGET to update versions here:
  * Scala version:
    * `build.sbt`
    * `dev/index-dev.html`
    * `publish.sh`

  * When bumping lib versions och scalajs-dom: `build.sbt`
  * When bumping scalajs plugin: `project/plugins.sbt`
  * When bumping sbt version:  `project/build.properties`

## Build for production

* When an update is ready for production, use the `publish.sh` bash script which includes the following commands where `3.x.y` is your current Scala version:

```bash
sbt "clean;fullLinkJS"
cp target/scala-3.x.y/muntabot-opt/main.js public/.
```

## Deploy

* Note: you need correct credentials to also upload the contents of the `public` folder to a public server. The web server needs the files `main.js`, `index.html` and `style.css`.

* To test before uploading to production you can install the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) and right-click `public/index.html` and click `Open with Live Server`.
