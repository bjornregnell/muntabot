#!/usr/bin/env bash
# Publish muntabot to fileadmin. NOTE: every step must fail LOUDLY -- without set -e a
# failed build used to sail on and scp whatever stale main.js happened to be in target/,
# so a broken build published silently as a successful one.
set -euo pipefail

sbt --client "clean;fullLinkJS"
scala-cli run auto-translate.sc
sbt --client "fullLinkJS"

# Do NOT pin the Scala version in this path: it moves whenever the project bumps Scala,
# and a hardcoded one fails the cp -- which, before set -e, meant publishing the previous
# bundle with no error shown at all.
MAIN_JS=$(ls target/scala-*/muntabot-opt/main.js)
[ -f "$MAIN_JS" ] || { echo "expected exactly one built main.js, found: $MAIN_JS" >&2; exit 1; }
cp "$MAIN_JS" public/.

ssh "$LUCATID@fileadmin.cs.lth.se" mkdir -p pgk/muntabot
scp public/* "$LUCATID@fileadmin.cs.lth.se:pgk/muntabot/."
