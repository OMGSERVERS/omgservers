package com.omgservers.ctl.operation.command.developer.project;

public interface DeveloperProjectCreateAliasOperation {

    void execute(String tenant,
                 Long projectId,
                 String alias,
                 String installation);
}
