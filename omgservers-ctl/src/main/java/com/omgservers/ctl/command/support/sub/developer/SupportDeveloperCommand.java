package com.omgservers.ctl.command.support.sub.developer;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.support.sub.developer.sub.SupportDeveloperCreateAliasCommand;
import com.omgservers.ctl.command.support.sub.developer.sub.SupportDeveloperCreateDeveloperCommand;
import com.omgservers.ctl.command.support.sub.developer.sub.SupportDeveloperDeleteDeveloperCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "developer",
        description = "Support commands for managing developers.",
        subcommands = {
                SupportDeveloperCreateDeveloperCommand.class,
                SupportDeveloperCreateAliasCommand.class,
                SupportDeveloperDeleteDeveloperCommand.class,
        })
public class SupportDeveloperCommand extends ParentCommand {
}
