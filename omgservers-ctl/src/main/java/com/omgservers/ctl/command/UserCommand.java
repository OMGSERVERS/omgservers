package com.omgservers.ctl.command;

import picocli.CommandLine;

public abstract class UserCommand implements Runnable {

    @CommandLine.Option(names = {"-s", "--service"},
            description = "Name of the service to send commands to.")
    protected String service;

    @CommandLine.Option(names = {"-u", "--user"},
            description = "Id or alias of the user on whose behalf the commands are sent.")
    protected String user;

    @CommandLine.Option(names = {"-pp", "--pretty-print"},
            description = "Pretty-print the output.")
    protected boolean prettyPrint;
}
