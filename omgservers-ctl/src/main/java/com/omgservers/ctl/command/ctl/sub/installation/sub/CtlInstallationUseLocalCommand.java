package com.omgservers.ctl.command.ctl.sub.installation.sub;

import com.omgservers.ctl.operation.command.ctl.installation.CtlInstallationUseLocalOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "use-local",
        description = "Use a locally running service installation.")
public class CtlInstallationUseLocalCommand implements Runnable {

    @Inject
    CtlInstallationUseLocalOperation ctlInstallationUseLocalOperation;

    @Override
    public void run() {
        ctlInstallationUseLocalOperation.execute();
    }
}
