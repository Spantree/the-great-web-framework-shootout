#!/bin/sh -x

# This script is a modied version of the vertx launcher.

PRG=`which vertx`
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# The path to this script from the place it was called
DIRNAME=`dirname "$PRG"`

# The absolute path of this script
CURRDIR=`pwd`
SCRIPTDIR=`cd $DIRNAME;pwd`
cd $CURRDIR

CLASSPATH=\
$DIRNAME/../conf:\
$DIRNAME/../lib/jars/vert.x-core.jar:\
$DIRNAME/../lib/jars/vert.x-platform.jar:\
$DIRNAME/../lib/jars/netty.jar:\
$DIRNAME/../lib/jars/jackson-core.jar:$DIRNAME/../lib/jars/jackson-mapper.jar:\
$DIRNAME/../lib/jars/hazelcast.jar:\
$JRUBY_HOME/lib/jruby.jar:\
$DIRNAME/../lib/jars/groovy.jar:\
$DIRNAME/../lib/jars/js.jar:\
$DIRNAME/../lib/ruby:\
$DIRNAME/../lib/javascript:\
lib/sqlitejdbc-v056.jar

java -Djava.util.logging.config.file=$DIRNAME/../conf/logging.properties -Djruby.home=$JRUBY_HOME \
-Dvertx.mods=$VERTX_MODS -Dvertx.install=$SCRIPTDIR/.. -cp $CLASSPATH org.vertx.java.deploy.impl.cli.VertxMgr\
 run App.groovy -cp src/

