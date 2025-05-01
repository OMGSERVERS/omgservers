package com.omgservers.ctl.operation.wal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.wal.WalDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ReadWalOperationImpl implements ReadWalOperation {

    final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public WalDto execute(final Path path) {
        final var fileName = path.toString();
        log.debug("Getting WAL from \"{}\"", fileName);

        try (final var reader = new BufferedReader(new FileReader(fileName))) {
            final var wal = new WalDto();
            wal.setPath(path);

            final var logLines = new ArrayList<LogLineDto>();
            wal.setLogLines(logLines);

            while (true) {
                final var line = reader.readLine();
                if (line != null) {
                    if (!line.isBlank()) {
                        final var log = objectMapper.readValue(line, LogLineDto.class);
                        logLines.add(log);
                    }
                } else {
                    break;
                }
            }
            Collections.reverse(logLines);

            log.debug("Parsed \"{}\" WAL lines", logLines.size());
            return wal;
        }
    }
}
