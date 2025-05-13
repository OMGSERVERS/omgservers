package com.omgservers.ctl.operation.command.developer.stage;

public interface DeveloperStageDeleteStageOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String installation);
}
