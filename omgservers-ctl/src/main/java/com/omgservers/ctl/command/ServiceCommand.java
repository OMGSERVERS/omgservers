package com.omgservers.ctl.command;

import picocli.CommandLine;

public abstract class ServiceCommand implements Runnable {

    @CommandLine.Option(names = {"-i", "--installation"},
            description = "Name of the installation to send commands to.")
    protected String installation;
}
