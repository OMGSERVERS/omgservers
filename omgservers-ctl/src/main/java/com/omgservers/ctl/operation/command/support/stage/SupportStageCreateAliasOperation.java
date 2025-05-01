package com.omgservers.ctl.operation.command.support.stage;

public interface SupportStageCreateAliasOperation {

    void execute(String tenant,
                 Long stageId,
                 String alias,
                 String service,
                 String user);
}
