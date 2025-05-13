package com.omgservers.ctl.command.admin.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.admin.AdminPingDockerHostOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.URI;

@Slf4j
@CommandLine.Command(
        name = "ping-docker-host",
        description = "Check the status of the specified Docker daemon.")
public class AdminPingDockerHostCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Host's docker daemon uri to ping.")
    URI dockerDaemonUri;

    @Inject
    AdminPingDockerHostOperation pingDockerHostOperation;

    @Override
    public void run() {
        pingDockerHostOperation.execute(dockerDaemonUri, installation);
    }
}
