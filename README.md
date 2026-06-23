# muntabot
![Build Status](https://github.com/bjornregnell/muntabot/actions/workflows/main.yml/badge.svg)

**muntabot — an oral exam (viva) assistant for programming**

Are you reskilling for the era of agentic software engineering? Test your programming abilities with [`muntabot`](https://fileadmin.cs.lth.se/pgk/muntabot) using pen, paper and [the Scala Quickref](https://fileadmin.cs.lth.se/pgk/quickref.pdf) only.

This single-page, client-only web app runs directly in your browser:

https://fileadmin.cs.lth.se/pgk/muntabot

(If you wonder about the peculiar name of this repo: "munta" is Swedish slang for oral examination.)

Muntabot is developed using [Scala 3](https://scala-lang.org/) and [Scala JS](https://www.scala-js.org/doc/tutorial/basic/), chosen for safety and token efficiency in agent-assisted development. Built using [`sbt`](https://www.scala-sbt.org/).


# How to develop

* Prerequisites: [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html)

* Clone or download this repo on your machine.

* The build creates `main.js` that is consumed by `index.html`, which in turn needs the `style.css` file for formatting.

* While developing type `~fastLinkJS` in `sbt` and open/reload the `dev/index-dev.html` file in your browser.

* If you are using a sand-boxed browser (e.g. Firefox installed as a snap) then local files are opened as something similar to `file:///run/user/1000/doc/ad1d72e5/index.html` which means that the index.html-page cannot access the accompanying local files main.js and style.css so you need to explicitly type the correct path in the URL field using something similar to  `file:///home/user/project/dev/index.html#muntabot` where you change the user and project in the path to where the files are located. Note the trippel slashes `///`

* To avoid having to reload the page upon every change the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) can be used to do this automatically. Install the extension in vscode and right-click `dev/index-dev.html` and click `Open with Live Server` after running `~fastLinkJS` in `sbt`. Don't forget to check that `dev/index-dev.html` points to the correct subdir of `target/scala-3.x.y/` ...

# Bilingual: how the English side is generated

muntabot is bilingual — Swedish (default) and English, selectable in the top-right dropdown. The Swedish content is authored by hand in `src/main/scala/data-sv.scala`; the **English files are generated** from it:

* `src/main/scala/data-en.scala`
* `src/main/scala/headings-En-GENERATED.scala`
* `src/main/scala/concept-headings-En-GENERATED.scala`

They are produced by `auto-translate.sc` — a standalone [Scala CLI](https://scala-cli.virtuslab.org/) script (deliberately kept out of the `sbt` build) that translates Swedish → English with a small local LLM via [Ollama](https://ollama.com/).

## Prerequisites for translating

* Install [Scala CLI](https://scala-cli.virtuslab.org/install).
* Install [Ollama](https://ollama.com/download) and pull the model: `ollama pull qwen2.5:3b` (see the [model page](https://ollama.com/library/qwen2.5)). It runs on CPU and needs ≈2 GB.

## Run the translator

From the repo root:

```bash
scala-cli run auto-translate.sc
```

`publish.sh` runs this automatically as part of a production build, so you normally only run it by hand after changing the Swedish side.

## How it works (deterministic and build-safe)

* **Cache-first.** Translations live in `translate-cache.tsv` (committed). The model is called **only for new or changed Swedish strings**, so re-running changes nothing and Ollama isn't even needed unless the Swedish content actually changed.
* **Deterministic.** The model uses a fixed seed and temperature 0, so a first-time translation is reproducible.
* **Authoritative terms win.** Official term translations in `src/main/scala/translations-GENERATED.scala` (generated from the introprog glossary) take precedence over the model.
* **Never breaks the build.** `$math$` and `` `code` `` spans are masked and kept verbatim; model output is validated and falls back to the Swedish text on any failure (worst case: English == Swedish). The normal `sbt` compile of the generated `.scala` files is the final guardrail.

# How to deploy 

## Bump versions

* Bump version of muntabot here: `public/index.html` 

* When bumping scala version: DON'T FORGET to update versions here:
  * Scala version:
    * `build.sbt`
    * `dev/index-dev.html`
    * `publish.sh`

  * When bumping lib versions och scalajs-dom update: `build.sbt`
  * When bumping scalajs plugin update: `project/plugins.sbt`
  * When bumping sbt version update:  `project/build.properties`

* IMPORTANT: after changing anything under `project/` (e.g. the `sbt-scalajs` plugin or the `sbt` version), **restart the sbt server** with `sbt --client shutdown` (or run a cold `sbt`). A long-running `sbt --client` server keeps the *old* build definition and won't pick up plugin changes — `publish.sh` uses `sbt --client`, so a stale server makes it build against the old plugin (e.g. an old `scalajs-library`) and fail.

* `auto-translate.sc` pins its **own** Scala and JVM versions in its `//> using` header — those belong to the translator (run by Scala CLI) and are independent of the app's Scala version, so they don't need to track it.

## Build for production

* When an update is ready for production, run the `publish.sh` script. It builds the app, regenerates the English translations with `auto-translate.sc` (see [the bilingual section](#bilingual-how-the-english-side-is-generated)), rebuilds, and uploads the `public/` folder. See `publish.sh` for the exact, current commands (not duplicated here so they can't drift out of date).

* That production build therefore needs Scala CLI and Ollama for the translation step. If you build on a machine without them, comment out the `scala-cli run auto-translate.sc` line in `publish.sh` — the already-committed English files compile on their own.

## Deploy

* Note: you need correct credentials to also upload the contents of the `public` folder to a public server. The web server needs the files `main.js`, `index.html` and `style.css`.

* To test before uploading to production you can install the vscode extension [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) and right-click `public/index.html` and click `Open with Live Server`.
