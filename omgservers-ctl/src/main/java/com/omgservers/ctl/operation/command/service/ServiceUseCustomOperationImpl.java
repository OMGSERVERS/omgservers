package com.omgservers.ctl.operation.command.service;

import com.omgservers.ctl.operation.wal.service.AppendServiceUrlOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;

@ApplicationScoped
@AllArgsConstructor
public class ServiceUseCustomOperationImpl implements ServiceUseCustomOperation {

    final AppendServiceUrlOperation appendServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String name,
                        final URI uri) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        appendServiceUrlOperation.execute(path, name, uri);
    }
}
