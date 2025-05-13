package com.omgservers.ctl.operation.command.support.project;

public interface SupportProjectCreateAliasOperation {

    void execute(String tenant,
                 Long projectId,
                 String alias,
                 String installation);
}
