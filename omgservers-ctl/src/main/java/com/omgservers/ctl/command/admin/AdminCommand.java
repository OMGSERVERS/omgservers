package com.omgservers.ctl.command.admin;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.admin.sub.AdminBcryptHashCommand;
import com.omgservers.ctl.command.admin.sub.AdminCalculateShardCommand;
import com.omgservers.ctl.command.admin.sub.AdminCreateTokenCommand;
import com.omgservers.ctl.command.admin.sub.AdminGenerateIdCommand;
import com.omgservers.ctl.command.admin.sub.AdminPingDockerHostCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "admin",
        description = "Manage service installation and configuration.",
        subcommands = {
                AdminCreateTokenCommand.class,
                AdminGenerateIdCommand.class,
                AdminBcryptHashCommand.class,
                AdminCalculateShardCommand.class,
                AdminPingDockerHostCommand.class,
        })
public class AdminCommand extends ParentCommand {
}
