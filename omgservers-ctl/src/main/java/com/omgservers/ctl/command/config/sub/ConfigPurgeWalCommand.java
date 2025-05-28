package com.omgservers.ctl.command.config.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.config.ConfigPurgeWalOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "purge-wal",
        description = "Purge WAL file.")
public class ConfigPurgeWalCommand extends InstallationCommand {

    @Inject
    ConfigPurgeWalOperation configPurgeWalOperation;

    @Override
    public void run() {
        configPurgeWalOperation.execute();
    }
}
