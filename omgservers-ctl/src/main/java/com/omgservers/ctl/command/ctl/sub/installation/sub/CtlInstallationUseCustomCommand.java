package com.omgservers.ctl.command.ctl.sub.installation.sub;

import com.omgservers.ctl.operation.command.ctl.installation.CtlInstallationUseCustomOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.URI;

@Slf4j
@CommandLine.Command(
        name = "use-custom",
        description = "Use a custom service installation.")
public class CtlInstallationUseCustomCommand implements Runnable {

    @CommandLine.Parameters(description = "Service name.")
    String name;

    @CommandLine.Parameters(description = "Api address.")
    URI api;

    @CommandLine.Parameters(description = "Registry address.")
    URI registry;

    @Inject
    CtlInstallationUseCustomOperation ctlInstallationUseCustomOperation;

    @Override
    public void run() {
        ctlInstallationUseCustomOperation.execute(name, api, registry);
    }
}
