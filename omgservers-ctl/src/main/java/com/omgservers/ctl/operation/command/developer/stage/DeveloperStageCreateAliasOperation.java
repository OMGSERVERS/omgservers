package com.omgservers.ctl.operation.command.developer.stage;

public interface DeveloperStageCreateAliasOperation {

    void execute(String tenant,
                 Long stageId,
                 String alias,
                 String installation);
}
