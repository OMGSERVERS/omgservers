package com.omgservers.ctl.operation.wal;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.ResultMapLogLineBodyDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AppendResultMapOperationImpl implements AppendResultMapOperation {

    final WriteLogLineOperation writeLogLineOperation;

    @Override
    public void execute(final Path path,
                        final KeyEnum key,
                        final String value) {
        execute(path, Collections.singletonMap(key, value));
    }

    @Override
    public void execute(final Path path, Map<KeyEnum, String> map) {

        final var logBody = new ResultMapLogLineBodyDto(map);

        final var logLine = new LogLineDto();
        logLine.setQualifier(logBody.getQualifier());
        logLine.setBody(logBody);

        writeLogLineOperation.execute(path, logLine);
    }
}
