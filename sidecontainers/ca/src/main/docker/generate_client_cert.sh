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
openssl genrsa -out $CLIENT/private_key.pem 4096

echo Generate $CLIENT certificate
openssl req -new -subj "/CN=$CLIENT" -key $CLIENT/private_key.pem -sha256 -out $CLIENT/client.csr

echo Generate extfile $CLIENT/extfile.cnf
echo "extendedKeyUsage = clientAuth" > $CLIENT/extfile.cnf

echo Sign $CLIENT certificate
openssl x509 -req -days 1095 -sha256 -in $CLIENT/client.csr -CA ca-cert/cert.pem -CAkey ca-cert/private_key.pem \
  -CAcreateserial -out $CLIENT/cert.pem -extfile $CLIENT/extfile.cnf

echo Extract public key
openssl x509 -in $CLIENT/cert.pem -pubkey -noout > $CLIENT/public_key.pem

echo Copy CA certificate
cp ca-cert/cert.pem $CLIENT/ca.pem

rm $CLIENT/client.csr $CLIENT/extfile.cnf
chmod 0400 $CLIENT/private_key.pem
chmod 0444 $CLIENT/cert.pem
chmod 0444 $CLIENT/ca.pem