package com.omgservers.ctl.operation.wal.service;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.ServiceUrlLogLineBodyDto;
import com.omgservers.ctl.operation.wal.WriteLogLineOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Path;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AppendServiceUrlOperationImpl implements AppendServiceUrlOperation {

    final WriteLogLineOperation writeLogLineOperation;

    @Override
    public void execute(final Path path,
                        final String name,
                        final URI uri) {

        final var logBody = new ServiceUrlLogLineBodyDto(name, uri);

        final var logLine = new LogLineDto();
        logLine.setQualifier(logBody.getQualifier());
        logLine.setBody(logBody);

        writeLogLineOperation.execute(path, logLine);
    }
}
