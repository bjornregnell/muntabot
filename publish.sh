sbt --client "clean;fullLinkJS"
scala-cli run auto-translate.sc
sbt --client "fullLinkJS"
cp target/scala-3.8.4/muntabot-opt/main.js public/.
ssh $LUCATID@fileadmin.cs.lth.se mkdir -p pgk/muntabot
scp public/* $LUCATID@fileadmin.cs.lth.se:pgk/muntabot/.
