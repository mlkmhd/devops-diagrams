#!/bin/bash
set -xe

helmfile template > resources.yaml
kustomize build . > resources-patched.yaml
kubectl delete -f sliced && true
rm -rf sliced/
kubectl-slice -f resources-patched.yaml -o sliced
kubectl apply -f sliced