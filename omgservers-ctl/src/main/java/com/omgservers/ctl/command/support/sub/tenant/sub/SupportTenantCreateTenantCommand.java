package com.omgservers.ctl.command.support.sub.tenant.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.tenant.SupportTenantCreateTenantOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-tenant",
        description = "Create a new tenant.")
public class SupportTenantCreateTenantCommand extends InstallationCommand {

    @Inject
    SupportTenantCreateTenantOperation supportTenantCreateTenantOperation;

    @Override
    public void run() {
        supportTenantCreateTenantOperation.execute(installation);
    }
}
