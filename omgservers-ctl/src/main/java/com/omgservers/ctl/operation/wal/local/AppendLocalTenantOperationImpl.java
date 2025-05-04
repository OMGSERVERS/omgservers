package com.omgservers.ctl.operation.wal.local;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.body.LocalTenantLogLineBodyDto;
import com.omgservers.ctl.operation.wal.WriteLogLineOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AppendLocalTenantOperationImpl implements AppendLocalTenantOperation {

    final WriteLogLineOperation writeLogLineOperation;

    @Override
    public void execute(final Path path,
                        final String developer,
                        final String password,
                        final String tenant,
                        final String project,
                        final String stage) {
        final var logBody = new LocalTenantLogLineBodyDto(developer,
                password,
                tenant,
                project,
                stage);

        final var logLine = new LogLineDto();
        logLine.setQualifier(logBody.getQualifier());
        logLine.setBody(logBody);

        writeLogLineOperation.execute(path, logLine);
    }
}
