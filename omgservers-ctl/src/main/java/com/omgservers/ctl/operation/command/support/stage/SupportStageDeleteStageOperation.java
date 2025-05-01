package com.omgservers.ctl.operation.command.support.stage;

public interface SupportStageDeleteStageOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String service,
                 String user);
}
