#!/bin/bash

docker pull topinambours/takenoko:demo
docker run -it --network host -v /var/run/docker.sock:/var/run/docker.sock topinambours/takenoko:demo "${@:1}"