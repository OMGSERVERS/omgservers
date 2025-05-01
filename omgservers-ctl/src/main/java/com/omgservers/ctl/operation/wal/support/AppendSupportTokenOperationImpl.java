package com.omgservers.ctl.operation.wal.support;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.SupportTokenLogLineBodyDto;
import com.omgservers.ctl.operation.wal.WriteLogLineOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AppendSupportTokenOperationImpl implements AppendSupportTokenOperation {

    final WriteLogLineOperation writeLogLineOperation;

    @Override
    public void execute(final Path path,
                        final String service,
                        final String user,
                        final String token) {

        final var logBody = new SupportTokenLogLineBodyDto(service, user, token);

        final var logLine = new LogLineDto();
        logLine.setQualifier(logBody.getQualifier());
        logLine.setBody(logBody);

        writeLogLineOperation.execute(path, logLine);
    }
}
