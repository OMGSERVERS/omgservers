package com.omgservers.ctl.operation.wal;

import com.omgservers.ctl.configuration.CtlConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDefaultWalPathOperationImpl implements GetDefaultWalPathOperation {

    @Override
    public Path execute() {
        final var fileName = CtlConfiguration.DEFAULT_WAL_FILENAME;
        final var path = Paths.get(System.getProperty("user.home"), fileName);
        return path;
    }
}
