#!/bin/bash

# Request edge certificate through letsencrypt

EMAIL=$1
DOMAIN=$2
if [ -z "${EMAIL}" -o -z "${DOMAIN}" ]; then
  echo "Usage: ./request_edge_certificate.sh <email> <domain>"
  exit 1
fi

certbot certonly --dry-run --manual --cert-name edgecertificate --preferred-challenges=dns --email ${EMAIL} --agree-tos -d ${DOMAIN}
