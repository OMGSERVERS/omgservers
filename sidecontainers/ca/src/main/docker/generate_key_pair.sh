#!/bin/bash
set -e

CLIENT=$1
if [ -z $CLIENT ]; then
  echo "Usage: ./generate_key_pair.sh <client>"
  exit 1
fi

if [ -d $CLIENT ]; then
  echo $CLIENT key pair has been already generated
  exit 0
fi

mkdir -p $CLIENT

echo Generate $CLIENT private key
openssl genrsa -out $CLIENT/private_key.pem 4096

echo Extract public part of key
openssl rsa -in $CLIENT/private_key.pem -pubout -out $CLIENT/public_key.pem

chmod 0400 $CLIENT/private_key.pem
chmod 0444 $CLIENT/public_key.pem