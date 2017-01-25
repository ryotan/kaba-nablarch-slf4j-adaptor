#!/bin/bash

if [[ ! $(builtin command -v jpm) ]]; then
  curl -sL https://github.com/jpm4j/jpm4j.installers/raw/master/dist/biz.aQute.jpm.run.jar > jpm-installer.jar
  java -jar jpm-installer.jar -u init
  rm jpm-installer.jar
fi

if [[ ! $(builtin command -v codacy-coverage-reporter) ]]; then
  jpm install com.codacy:codacy-coverage-reporter:assembly
fi
