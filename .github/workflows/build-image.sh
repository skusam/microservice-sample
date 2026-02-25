#!/usr/bin/env bash

set -e

branch="$( git rev-parse --abbrev-ref HEAD )"
oci_repository="$( basename `git rev-parse --show-toplevel` )"

docker build \
  --build-arg BUILD_DATE="$( date -u +'%Y-%m-%dT%H:%M:%SZ' )" \
  --build-arg REVISION="$( git rev-parse --verify HEAD )" \
  --tag "${oci_repository}" .

#trivy image --clear-cache
#trivy image --exit-code 1 --ignore-policy ./.github/workflows/trivy.rego "${oci_repository}"
#trivy image --exit-code 0 --ignore-policy ./.github/workflows/trivy.rego "${oci_repository}"

if [[ -z ${GITHUB_ACTIONS} ]]; then
  >&2 echo "Docker push is intended for GitHub actions"
  exit 0
fi

#if [[ ${branch} != "develop" ]]; then
#  >&2 echo "Docker push is intended for the develop branch"
#  exit 0
#fi

echo "${IMAGE_REGISTRY_TOKEN}" | \
  docker login --username mskusa --password-stdin

#tag="${GITHUB_RUN_NUMBER}"
tag=latest
remote_image="mskusa/${oci_repository}:${tag}"
docker tag "${oci_repository}" "${remote_image}"
docker push "${remote_image}"