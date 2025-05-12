package com.omgservers.ctl.exception;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
public class ExceptionHandler implements CommandLine.IExecutionExceptionHandler {

    @Override
    public int handleExecutionException(final Exception e,
                                        final CommandLine commandLine,
                                        final CommandLine.ParseResult fullParseResult) {
        log.error("Failed, {}", e.getMessage());
        return commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
