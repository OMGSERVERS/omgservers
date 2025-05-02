package com.omgservers.ctl.operation.command.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.exception.CommandException;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetOperationImpl implements GetOperation {

    final GetWalOperation getWalOperation;

    final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void execute(final KeyEnum key) {
        final var wal = getWalOperation.execute();

        if (wal.getLogLines().isEmpty()) {
            throw new CommandException("no data found to get key");
        }

        final var value = Optional.ofNullable(wal.getLogLines().getFirst())
                .map(logLine -> {
                    final var map = objectMapper.convertValue(logLine.getBody(),
                            new TypeReference<Map<KeyEnum, String>>() {
                            });
                    return map.get(key);
                })
                .orElseThrow(() -> new CommandException("key not found to get value"));

        System.err.println(value);
    }
}
