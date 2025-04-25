package com.omgservers.ctl.operation.command.developer.tenant;

public interface DeveloperTenantGetDetailsOperation {

    void execute(String tenant,
                 String service,
                 String user,
                 boolean prettyPrint);
}
