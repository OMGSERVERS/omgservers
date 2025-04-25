package com.omgservers.ctl.command.developer.sub.version;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.version.sub.DeveloperVersionCreateImageCommand;
import com.omgservers.ctl.command.developer.sub.version.sub.DeveloperVersionCreateVersionCommand;
import com.omgservers.ctl.command.developer.sub.version.sub.DeveloperVersionDeleteVersionCommand;
import com.omgservers.ctl.command.developer.sub.version.sub.DeveloperVersionGetDetailsCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "version",
        description = "Developer commands for managing versions.",
        subcommands = {
                DeveloperVersionCreateVersionCommand.class,
                DeveloperVersionGetDetailsCommand.class,
                DeveloperVersionDeleteVersionCommand.class,
                DeveloperVersionCreateImageCommand.class,
        })
public class DeveloperVersionCommand extends ParentCommand {
}
