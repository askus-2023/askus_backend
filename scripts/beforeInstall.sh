#!/usr/bin/env bash

if [ -d /home/ubuntu/app/ ]; then
    sudo rm -rf /home/ubuntu/app/
fi
sudo mkdir -vp /home/ubuntu/app/
sudo chown -R ubuntu:ubuntu /home/ubuntu/app
sudo chmod ugo+rwx /home/ubuntu/app/**
