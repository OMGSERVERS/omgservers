package com.omgservers.ctl.command.config.sub;

import com.omgservers.ctl.operation.command.service.ServiceUseCustomOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.URI;

@Slf4j
@CommandLine.Command(
        name = "use-custom",
        description = "Use a custom service installation.")
public class ServiceUseCustomCommand implements Runnable {

    @CommandLine.Parameters(description = "Service name.")
    String name;

    @CommandLine.Parameters(description = "Service url address.")
    String url;

    @Inject
    ServiceUseCustomOperation serviceUseCustomOperation;

    @Override
    public void run() {
        serviceUseCustomOperation.execute(name, URI.create(url));
    }
}
