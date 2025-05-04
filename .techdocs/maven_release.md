## Release Workflow

1. Create an issue titled **"Release X.Y.Z version"** and check out the corresponding branch
2. Run:
   ```bash
   mvn versions:set -DremoveSnapshot -DgenerateBackupPoms=false
   ```
3. Update the `VERSION` variable in `install.sh`
4. Update the **"How to try it"** section in `README.md`
5. Update all `OMGSERVERS_VERSION` variable values in `.env` files
6. Commit and create a pull request to merge into `main`
7. Wait for the build pipelines to finish
8. Review and publish the newly created draft release

## Setting the Next Version

1. Create an issue titled **"Set X.Y.(Z+1)-SNAPSHOT version"** and check out the corresponding branch
2. Run:
   ```bash
   mvn versions:set -DnewVersion=X.Y.(Z+1)-SNAPSHOT -DgenerateBackupPoms=false
   ```
3. Commit and create a pull request to merge into `main`
