package com.omgservers.ctl.command.support.sub.tenant;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.SupportTenantCreateAliasCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.SupportTenantCreatePermissionCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.SupportTenantCreateTenantCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.SupportTenantDeletePermissionCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.SupportTenantDeleteTenantCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "tenant",
        description = "Manage service tenants.",
        subcommands = {
                SupportTenantCreateTenantCommand.class,
                SupportTenantCreateAliasCommand.class,
                SupportTenantDeleteTenantCommand.class,
                SupportTenantCreatePermissionCommand.class,
                SupportTenantDeletePermissionCommand.class,
        })
public class SupportTenantCommand extends ParentCommand {
}
