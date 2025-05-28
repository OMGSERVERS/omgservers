package com.omgservers.ctl.command.config.sub.installation.sub;

import com.omgservers.ctl.operation.command.config.installation.ConfigInstallationUseCustomOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.URI;

@Slf4j
@CommandLine.Command(
        name = "use-custom",
        description = "Use a custom service installation.")
public class ConfigInstallationUseCustomCommand implements Runnable {

    @CommandLine.Parameters(description = "Service name.")
    String name;

    @CommandLine.Parameters(description = "Api address.")
    URI api;

    @CommandLine.Parameters(description = "Registry address.")
    URI registry;

    @Inject
    ConfigInstallationUseCustomOperation configInstallationUseCustomOperation;

    @Override
    public void run() {
        configInstallationUseCustomOperation.execute(name, api, registry);
    }
}
