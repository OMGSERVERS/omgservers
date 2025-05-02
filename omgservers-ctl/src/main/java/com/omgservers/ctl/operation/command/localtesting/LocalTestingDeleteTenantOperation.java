package com.omgservers.ctl.operation.command.localtesting;

public interface LocalTestingDeleteTenantOperation {

    void execute(String tenant, String project, String stage);
}
