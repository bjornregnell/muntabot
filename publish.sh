sbt --client fullLinkJS
ssh $LUCATID@fileadmin.cs.lth.se mkdir -p pgk/muntabot
scp target/scala-3.0.0/muntabot-opt/main.js $LUCATID@fileadmin.cs.lth.se:pgk/muntabot/.
scp index.html $LUCATID@fileadmin.cs.lth.se:pgk/muntabot/.
