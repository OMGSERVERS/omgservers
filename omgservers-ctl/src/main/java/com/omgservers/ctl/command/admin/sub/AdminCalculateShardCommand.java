package com.omgservers.ctl.command.admin.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.admin.AdminCalculateShardOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "calculate-shard",
        description = "Calculate shard server based on the provided key.")
public class AdminCalculateShardCommand extends UserCommand {

    @CommandLine.Parameters(description = "Key to calculate shard.")
    String key;

    @Inject
    AdminCalculateShardOperation adminCalculateShardOperation;

    @Override
    public void run() {
        adminCalculateShardOperation.execute(key, installation, user);
    }
}
