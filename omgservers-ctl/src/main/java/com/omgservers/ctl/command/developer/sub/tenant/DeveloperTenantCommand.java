package com.omgservers.ctl.command.developer.sub.tenant;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.tenant.sub.DeveloperTenantGetDetailsCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "tenant",
        description = "Developer commands for managing tenants.",
        subcommands = {
                DeveloperTenantGetDetailsCommand.class
        })
public class DeveloperTenantCommand extends ParentCommand {
}
