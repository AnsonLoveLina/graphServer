#!/bin/sh
serverHome=/home/dev/graphServer
time=$(date '+%Y%m%d-%H%M%S' )
mkdir -p $serverHome/temp backup bin bin/logs
mv $serverHome/bin $serverHome/backup/$time
mkdir -p $serverHome/bin bin/logs
cp $serverHome/temp $serverHome/bin
jps | grep -E '* graphServer*' | awk '{print $1}' | while read pid
do
    kill -9 $pid
done
java -Xms64m -Xmx256m -jar ./graphServer-1.0-SNAPSHOT.jar >> $serverHome/bin/logs/graphServer.log
;
