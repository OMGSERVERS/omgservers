package com.omgservers.ctl.operation.command.support.installation;

public interface SupportInstallationInitTenantOperation {

    void execute(String developer,
                 String tenant,
                 String project,
                 String stage,
                 String installation);
}
