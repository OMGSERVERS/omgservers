package com.omgservers.ctl.operation.command.developer.stage;

public interface DeveloperStageCreateStageOperation {

    void execute(String tenant,
                 String project,
                 String service,
                 String user);
}
