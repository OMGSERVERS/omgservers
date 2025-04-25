package com.omgservers.ctl.command.get;

import com.omgservers.ctl.command.get.converter.KeyConverter;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.command.get.GetOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "get",
        description = "Retrieve the latest command result by key.")
public class GetCommand implements Runnable {

    @CommandLine.Parameters(description = "Key of value.",
            converter = KeyConverter.class)
    KeyEnum key;

    @Inject
    GetOperation getOperation;

    @Override
    public void run() {
        getOperation.execute(key);
    }
}
