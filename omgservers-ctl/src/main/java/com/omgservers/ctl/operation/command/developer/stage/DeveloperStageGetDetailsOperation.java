package com.omgservers.ctl.operation.command.developer.stage;

public interface DeveloperStageGetDetailsOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String service,
                 String user,
                 boolean prettyPrint);
}
