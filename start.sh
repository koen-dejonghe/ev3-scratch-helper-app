#!/bin/bash

mkdir -p logs

nohup ./gradlew bootRun >> logs/ev3-scratch-helper-app.log 2>&1 &
# less logs/ev3-scratch-helper-app.log

