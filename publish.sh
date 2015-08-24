#!/bin/bash

set -ex

lein clean
lein cljsbuild once min

deploy_dir=deploy

if [ ! -d "$deploy_dir" ]; then
  git clone git@github.com:shaunlebron/gh4st.git $deploy_dir
fi

# Commit and push
pushd $deploy_dir
git reset HEAD .
git rm -rf *
cp -r ../resources/public/ .
git add -u
git add .
git commit -m "update deploy"
git push origin master:gh-pages

