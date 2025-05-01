package com.omgservers.ctl.command.config.sub;

import com.omgservers.ctl.operation.command.service.ServiceUseLocalOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "use-local",
        description = "Use a locally running service installation.")
public class ServiceUseLocalCommand implements Runnable {

    @Inject
    ServiceUseLocalOperation serviceUseLocalOperation;

    @Override
    public void run() {
        serviceUseLocalOperation.execute();
    }
}
