package com.omgservers.ctl.operation.command.developer.version;

public interface DeveloperVersionGetDetailsOperation {

    void execute(String tenant,
                 String project,
                 String version,
                 String installation);
}
