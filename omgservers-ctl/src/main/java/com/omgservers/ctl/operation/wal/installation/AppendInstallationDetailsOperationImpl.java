package com.omgservers.ctl.operation.wal.installation;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.body.InstallationDetailsLogLineBodyDto;
import com.omgservers.ctl.operation.wal.WriteLogLineOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Path;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AppendInstallationDetailsOperationImpl implements AppendInstallationDetailsOperation {

    final WriteLogLineOperation writeLogLineOperation;

    @Override
    public void execute(final Path path,
                        final String name,
                        final URI address,
                        final URI registry) {
        final var logBody = new InstallationDetailsLogLineBodyDto(name, address, registry);

        final var logLine = new LogLineDto();
        logLine.setQualifier(logBody.getQualifier());
        logLine.setBody(logBody);

        writeLogLineOperation.execute(path, logLine);
    }
}
