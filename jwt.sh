#!/bin/bash
jq -R 'split(".") | .[0] | @base64d | fromjson' <<< $@
jq -R 'split(".") | .[1] | @base64d | fromjson' <<< $@