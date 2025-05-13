package com.omgservers.ctl.command.ctl.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.ctl.CtlPurgeWalOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "purge-wal",
        description = "Purge WAL file.")
public class CtlPurgeWalCommand extends InstallationCommand {

    @Inject
    CtlPurgeWalOperation ctlPurgeWalOperation;

    @Override
    public void run() {
        ctlPurgeWalOperation.execute();
    }
}
