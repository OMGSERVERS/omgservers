package com.omgservers.ctl.command;

import picocli.CommandLine;

public abstract class InstallationCommand implements Runnable {

    @CommandLine.Option(names = {"-n", "--installation"},
            description = "Name of the installation to send commands to.")
    protected String installation;
}
