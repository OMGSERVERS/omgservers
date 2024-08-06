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
openssl genrsa -out $SERVER/private_key.pem 4096

echo Generate $SERVER certificate
openssl req -new -subj "/CN=$SERVER" -key $SERVER/private_key.pem -sha256 -out $SERVER/server.csr

echo Generate extfile $SERVER/extfile.cnf
echo "subjectAltName = DNS:$SERVER" > $SERVER/extfile.cnf

echo Sign $SERVER certificate
openssl x509 -req -days 1095 -sha256 -in $SERVER/server.csr -CA ca-cert/cert.pem -CAkey ca-cert/private_key.pem \
  -CAcreateserial -out $SERVER/cert.pem -extfile $SERVER/extfile.cnf

echo Extract public key
openssl x509 -in $SERVER/cert.pem -pubkey -noout > $SERVER/public_key.pem

echo Copy CA certificate
cp ca-cert/cert.pem $SERVER/ca.pem

rm $SERVER/server.csr $SERVER/extfile.cnf
chmod 0400 $SERVER/private_key.pem
chmod 0444 $SERVER/cert.pem
chmod 0444 $SERVER/ca.pem
