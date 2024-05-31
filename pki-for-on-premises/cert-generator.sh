#!/bin/bash
set -ex

CERTIFICATES_COUNT=3

cert_dir=`dirname "$BASH_SOURCE"`/certs

rm -rf ${cert_dir}

echo "Generating new certificates"

mkdir -p ${cert_dir}

step certificate create root.io ${cert_dir}/root-cert.pem ${cert_dir}/root-ca.key \
  --profile root-ca --no-password --insecure --san root.io \
  --not-after 87600h --kty RSA

for ((i=1;i<=${CERTIFICATES_COUNT};i++)); do
    mkdir -p ${cert_dir}/cluster-${i}
    
    step certificate create \
      cluster-${i}.intermediate.io ${cert_dir}/cluster-${i}/ca-cert.pem \
      ${cert_dir}/cluster-${i}/ca-key.pem --ca ${cert_dir}/root-cert.pem \
      --ca-key ${cert_dir}/root-ca.key --profile intermediate-ca \
      --not-after 87600h --no-password --insecure \
      --san cluster-${i}.intermediate.io --kty RSA 

    cat ${cert_dir}/cluster-${i}/ca-cert.pem ${cert_dir}/root-cert.pem > ${cert_dir}/cluster-${i}/cert-chain.pem
done