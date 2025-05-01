package com.omgservers.ctl.command;

import picocli.CommandLine;

public class ParentCommand implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}
