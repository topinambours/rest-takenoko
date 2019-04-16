#!/bin/sh

if [ $# -eq 0 ]
  	then
    		echo "Missing argument, must be one of {server, client}"
	else
		case $1 in
			"server")
			exec java -jar ./server/target/server-1.0-SNAPSHOT.jar $2 
			;;
			"client")
			exec java -jar ./client/target/client-1.0-SNAPSHOT.jar $2 $3 $4
			;;
		*)
			echo "Wrong argument, must be one of {server, client}"
			;;
		esac
fi

