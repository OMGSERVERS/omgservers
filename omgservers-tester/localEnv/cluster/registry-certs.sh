#!/bin/bash

openssl req -newkey rsa:4096 -nodes -sha256 -keyout registry-certs/registry.key -x509 -days 365 -out registry-certs/registry.crt