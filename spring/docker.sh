#!/usr/bin/env bash
docker build -t spring .
docker run --init -p 4200:4200 -p 3001:3001 -it spring
