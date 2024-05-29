#!/bin/bash
set -xe

apt update
apt install curl jq -y

update-ca-certificates

# installing helm
curl -L 'https://get.helm.sh/helm-v3.13.3-linux-amd64.tar.gz' -o helm.tar.gz
tar -xvzf helm.tar.gz
mv linux-amd64/helm /usr/bin/
rm helm.tar.gz
rm -rf linux-amd64

# helm-diff plugin installation
curl -L 'https://github.com/databus23/helm-diff/releases/latest/download/helm-diff-linux-amd64.tgz' -o helm-diff.tgz
tar -xvzf helm-diff.tgz
helm plugin install ./diff || true
rm helm-diff.tgz

# installing helmfile
curl -L 'https://github.com/helmfile/helmfile/releases/download/v0.160.0/helmfile_0.160.0_linux_amd64.tar.gz' -o helmfile.tar.gz
tar -xvzf helmfile.tar.gz
mv helmfile /usr/bin
rm helmfile.tar.gz

# installing kubectl
curl -LO https://dl.k8s.io/release/v1.29.0/bin/linux/amd64/kubectl
chmod +x kubectl
mv kubectl /usr/bin

# installing kustomize
curl -L 'https://github.com/kubernetes-sigs/kustomize/releases/download/kustomize%2Fv3.8.1/kustomize_v3.8.1_linux_amd64.tar.gz' -o kustomize.tar.gz
tar -xvzf kustomize.tar.gz
mv kustomize /usr/bin
rm kustomize.tar.gz

# installing kubectl-slice
curl -L 'https://github.com/patrickdappollonio/kubectl-slice/releases/download/v1.2.7/kubectl-slice_linux_x86_64.tar.gz' -o kubectl-slice.tar.gz
tar -xvzf kubectl-slice.tar.gz
chmod +x kubectl-slice
mv kubectl-slice /usr/bin
rm kubectl-slice.tar.gz

# installing gpg and sops
apt install gpg gpg-agent -y
cd /tmp
curl -L https://github.com/getsops/sops/releases/download/v3.8.1/sops_3.8.1_amd64.deb -o sops.deb
dpkg -i sops.deb
rm sops.deb
