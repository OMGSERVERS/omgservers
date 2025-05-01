package com.omgservers.ctl.operation.command.developer.version;

public interface DeveloperVersionDeleteVersionOperation {

    void execute(String tenant,
                 String project,
                 String version,
                 String service,
                 String user);
}
