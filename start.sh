#!/bin/bash

mkdir -p logs

./gradlew bootRun > logs/ev3-scratch-helper-app.log 2>&1

