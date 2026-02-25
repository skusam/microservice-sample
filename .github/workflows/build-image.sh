#!/usr/bin/env bash

set -e

oci_repository="$( basename `git rev-parse --show-toplevel` )"

docker build \
  --file ./src/main/docker/Dockerfile.jvm \
  --build-arg BUILD_DATE="$( date -u +'%Y-%m-%dT%H:%M:%SZ' )" \
  --build-arg REVISION="$( git rev-parse --verify HEAD )" \
  --tag "${oci_repository}" .

if [[ -z ${GITHUB_ACTIONS} ]]; then
  >&2 echo "Docker push is intended for GitHub actions"
  exit 0
fi

echo "${IMAGE_REGISTRY_TOKEN}" | \
  docker login --username mskusa --password-stdin

tag=latest
remote_image="mskusa/${oci_repository}:${tag}"
docker tag "${oci_repository}" "${remote_image}"
docker push "${remote_image}"