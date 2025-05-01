package com.omgservers.ctl.command;

import picocli.CommandLine;

public abstract class ServiceCommand implements Runnable {

    @CommandLine.Option(names = {"-s", "--service"},
            description = "Name of the service to send commands to.")
    protected String service;
}
