## Release Workflow

1. Create and checkout release/X.Y.Z version
2. Run:
   ```bash
   mvn versions:set -DremoveSnapshot -DgenerateBackupPoms=false
   ```
3. Update the `VERSION` variable in `install.sh`
4. Update the **"How to try it"** section in `README.md`
5. Update all `OMGSERVERS_VERSION` variable values in `.env` files, set to X.Y.Z version
6. Commit and push changes
7. Verify build pipelines is successfully finished
8. Create and push tag X.Y.Z
9. Wait for the build pipelines to complete
10. Review and publish the newly created draft release

## Setting the Next Version

1. Checkout main branch
2. Run:
   ```bash
   mvn versions:set -DnewVersion=X.(Y+1).Z-SNAPSHOT -DgenerateBackupPoms=false
   ```
3. Update the `VERSION` variable in `install.sh`
4. Update the **"How to try it"** section in `README.md`
5. Update all `OMGSERVERS_VERSION` variable values in `.env` files, set to X.Y.Z version
6Commit and push changes
