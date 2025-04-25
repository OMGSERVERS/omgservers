package com.omgservers.ctl.command.support.sub.developer.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.developer.SupportDeveloperCreateDeveloperOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-developer",
        description = "Create a new developer account.")
public class SupportDeveloperCreateDeveloperCommand extends UserCommand {

    @Inject
    SupportDeveloperCreateDeveloperOperation supportDeveloperCreateDeveloperOperation;

    @Override
    public void run() {
        supportDeveloperCreateDeveloperOperation.execute(service, user);
    }
}
