#!/bin/bash

OS=${1:?"Please provide OS type (windows/linux/mac/mac_aarch64)"}

echo "Starting build..."

if [ "$OS" == "windows" ]; then
    J_ARG="@jpackage/windows"
elif [ "$OS" == "linux" ]; then
    J_ARG="@jpackage/linux"
else
    J_ARG="@jpackage/mac"
fi

clj -X:uberjar
jpackage @jpackage/common $J_ARG