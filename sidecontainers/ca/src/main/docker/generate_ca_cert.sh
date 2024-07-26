#!/bin/bash
set -e

HOST="ca.omgservers.com"

if [ -d "ca-cert" ]; then
  echo CA certificate has been already generated
  exit 0
fi

mkdir -p ca-cert

echo Generate CA private key
openssl genrsa -out ca-cert/private_key.pem 4096

echo Generate CA certificate
openssl req -new -x509 -days 1095 -subj "/CN=$HOST" -key ca-cert/private_key.pem -sha256 -out ca-cert/cert.pem

echo Extract public key
openssl x509 -in ca-cert/cert.pem -pubkey -noout > ca-cert/public_key.pem
