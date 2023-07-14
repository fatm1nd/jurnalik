#!/bin/bash

echo "First!"
# Start the first process
python3 ./bot.py &

echo "Second!"

# Start the second process
python3 ./core.py

