package com.omgservers.ctl.command.config;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.config.sub.ServiceUseCloudCommand;
import com.omgservers.ctl.command.config.sub.ServiceUseCustomCommand;
import com.omgservers.ctl.command.config.sub.ServiceUseLocalCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "config",
        description = "Commands for managing CTL configuration.",
        subcommands = {
                ServiceUseLocalCommand.class,
                ServiceUseCloudCommand.class,
                ServiceUseCustomCommand.class,
        })
public class ConfigCommand extends ParentCommand {
}
