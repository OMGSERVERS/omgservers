package com.omgservers.ctl.operation.wal;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.nio.file.Path;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PurgeWalOperationImpl implements PurgeWalOperation {

    final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void execute(final Path path) {
        final var fileName = path.toString();
        log.debug("Purging \"{}\" WAL file ", fileName);
        new FileWriter(fileName, false).close();
    }
}
