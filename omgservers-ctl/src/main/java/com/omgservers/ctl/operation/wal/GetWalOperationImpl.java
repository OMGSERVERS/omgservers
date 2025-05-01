package com.omgservers.ctl.operation.wal;

import com.omgservers.ctl.dto.wal.WalDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetWalOperationImpl implements GetWalOperation {

    final GetDefaultWalPathOperation getDefaultWalPathOperation;
    final ReadWalOperation readWalOperation;

    @Override
    @SneakyThrows
    public WalDto execute() {
        final var path = getDefaultWalPathOperation.execute();

        if (Files.exists(path)) {
            final var wal = readWalOperation.execute(path);
            return wal;
        } else {
            Files.createFile(path);
            log.debug("Empty WAL file \"{}\" created", path);
            return WalDto.create(path);
        }
    }
}
