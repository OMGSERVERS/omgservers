package com.omgservers.ctl.command.config.sub.installation.sub;

import com.omgservers.ctl.operation.command.config.installation.ConfigInstallationUseCustomOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.URI;
import java.util.Objects;

@Slf4j
@CommandLine.Command(
        name = "use-custom",
        description = "Use a custom service installation.")
public class ConfigInstallationUseCustomCommand implements Runnable {

    @CommandLine.Parameters(description = "Installation name.")
    String name;

    @CommandLine.Parameters(description = "Installation address.")
    URI address;

    @CommandLine.Option(names = {"-r", "--registry"},
            description = "Custom address of the installation registry.")
    URI registry;

    @Inject
    ConfigInstallationUseCustomOperation configInstallationUseCustomOperation;

    @Override
    public void run() {
        configInstallationUseCustomOperation.execute(name,
                address,
                Objects.isNull(registry) ? address : registry);
    }
}
