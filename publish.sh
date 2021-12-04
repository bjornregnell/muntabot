sbt fullLinkJS
cp target/scala-3.1.0/muntabot-opt/main.js public/.
ssh $LUCATID@fileadmin.cs.lth.se mkdir -p pgk/muntabot
scp public/* $LUCATID@fileadmin.cs.lth.se:pgk/muntabot/.
