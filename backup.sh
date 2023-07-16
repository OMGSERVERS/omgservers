#!/bin/bash
set -e

./cleanup.sh

zip -x "*outdated*" -x "*.idea*" -x "*.zip" -r omgservers-backup-$(date '+%Y-%m-%d').zip .