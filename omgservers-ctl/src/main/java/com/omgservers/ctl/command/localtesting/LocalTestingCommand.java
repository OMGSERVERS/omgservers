package com.omgservers.ctl.command.localtesting;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.localtesting.sub.LocalTestingDeleteTenantCommand;
import com.omgservers.ctl.command.localtesting.sub.LocalTestingDeployVersionCommand;
import com.omgservers.ctl.command.localtesting.sub.LocalTestingInitTenantCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "localtesting",
        description = "Commands for local testing.",
        subcommands = {
                LocalTestingInitTenantCommand.class,
                LocalTestingDeployVersionCommand.class,
                LocalTestingDeleteTenantCommand.class,
        })
public class LocalTestingCommand extends ParentCommand {
}
