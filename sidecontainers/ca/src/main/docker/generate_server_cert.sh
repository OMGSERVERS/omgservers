#!/bin/bash
set -e

SERVER=$1
if [ -z $SERVER ]; then
  echo "Usage: ./generate_server_cert.sh <server>"
  exit 1
fi

if [ -d $SERVER ]; then
  echo $SERVER certificate has been already generated
  exit 0
fi

mkdir -p $SERVER

echo Generate $SERVER private key
openssl genrsa -out $SERVER/server.key 4096

echo Generate $SERVER certificate
openssl req -new -subj "/CN=$SERVER" -key $SERVER/server.key -sha256 -out $SERVER/server.csr

echo Generate extfile $SERVER/extfile.cnf
echo "subjectAltName = DNS:$SERVER" > $SERVER/extfile.cnf

echo Sign $SERVER certificate
openssl x509 -req -days 1095 -sha256 -in $SERVER/server.csr -CA ca-cert/ca.crt -CAkey ca-cert/ca.key \
  -CAcreateserial -out $SERVER/server.crt -extfile $SERVER/extfile.cnf

echo Copy CA certificate
cp ca-cert/ca.crt $SERVER/ca.pem

rm $SERVER/server.csr $SERVER/extfile.cnf
chmod 0400 $SERVER/server.key
chmod 0444 $SERVER/server.crt
chmod 0444 $SERVER/ca.pem
