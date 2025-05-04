package com.omgservers.ctl.command;

import picocli.CommandLine;

public abstract class UserCommand implements Runnable {

    @CommandLine.Option(names = {"-i", "--installation"},
            description = "Name of the installation to send commands to.")
    protected String installation;

    @CommandLine.Option(names = {"-u", "--user"},
            description = "Id or alias of the user on whose behalf the commands are sent.")
    protected String user;

    @CommandLine.Option(names = {"-pp", "--pretty-print"},
            description = "Pretty-print the output.")
    protected boolean prettyPrint;
}
