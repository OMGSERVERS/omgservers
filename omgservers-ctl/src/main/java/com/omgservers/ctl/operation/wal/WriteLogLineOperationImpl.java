package com.omgservers.ctl.operation.wal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.operation.ctl.OutputObjectOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WriteLogLineOperationImpl implements WriteLogLineOperation {

    final OutputObjectOperation outputObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void execute(final Path path,
                        final LogLineDto logLine) {
        final var line = objectMapper.writeValueAsString(logLine);
        Files.write(path, Collections.singletonList(line), StandardOpenOption.APPEND);

        final var map = objectMapper.convertValue(logLine.getBody(), new TypeReference<Map<KeyEnum, String>>() {
        });

        if (map.containsKey(KeyEnum.TOKEN)) {
            map.put(KeyEnum.TOKEN, "<hidden>");
        }

        if (map.containsKey(KeyEnum.PASSWORD)) {
            map.put(KeyEnum.PASSWORD, "<hidden>");
        }

        outputObjectOperation.execute(map);
    }
}
