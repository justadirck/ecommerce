#!/usr/bin/env bash
docker build -t authzero .
docker run --init -p 4200:4200 -p 3001:3001 -it authzero
