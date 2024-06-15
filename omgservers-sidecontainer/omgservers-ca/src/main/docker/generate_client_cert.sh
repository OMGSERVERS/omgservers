#!/bin/bash
set -e

CLIENT=$1
if [ -z $CLIENT ]; then
  echo "Usage: ./generate_client_cert.sh <client>"
  exit 1
fi

if [ -d $CLIENT ]; then
  echo $CLIENT certificate has been already generated
  exit 0
fi

mkdir -p $CLIENT

echo Generate $CLIENT private key
openssl genrsa -out $CLIENT/client.key 4096

echo Generate $CLIENT certificate
openssl req -new -subj "/CN=$CLIENT" -key $CLIENT/client.key -sha256 -out $CLIENT/client.csr

echo Generate extfile $CLIENT/extfile.cnf
echo "extendedKeyUsage = clientAuth" > $CLIENT/extfile.cnf

echo Sign $CLIENT certificate
openssl x509 -req -days 1095 -sha256 -in $CLIENT/client.csr -CA ca-cert/ca.crt -CAkey ca-cert/ca.key \
  -CAcreateserial -out $CLIENT/client.crt -extfile $CLIENT/extfile.cnf

echo Copy CA certificate
cp ca-cert/ca.crt $CLIENT/ca.pem

rm $CLIENT/client.csr $CLIENT/extfile.cnf
chmod 0400 $CLIENT/client.key
chmod 0444 $CLIENT/client.crt
chmod 0444 $CLIENT/ca.pem