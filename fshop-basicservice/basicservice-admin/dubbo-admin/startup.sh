#!/bin/bash

docker-compose -f docker-compose.yml -p dubbo-admin down || true &&
docker-compose -f docker-compose.yml -p dubbo-admin up -d
