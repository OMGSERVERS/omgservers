package com.omgservers.ctl.command.config.sub.installation.sub;

import com.omgservers.ctl.operation.command.config.installation.ConfigInstallationUseLocalOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "use-local",
        description = "Use a locally running service installation.")
public class ConfigInstallationUseLocalCommand implements Runnable {

    @Inject
    ConfigInstallationUseLocalOperation configInstallationUseLocalOperation;

    @Override
    public void run() {
        configInstallationUseLocalOperation.execute();
    }
}
