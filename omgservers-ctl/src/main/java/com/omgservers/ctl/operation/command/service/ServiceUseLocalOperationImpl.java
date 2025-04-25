package com.omgservers.ctl.operation.command.service;

import com.omgservers.ctl.operation.wal.service.AppendServiceUrlOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;

@ApplicationScoped
@AllArgsConstructor
public class ServiceUseLocalOperationImpl implements ServiceUseLocalOperation {

    final AppendServiceUrlOperation appendServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute() {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var name = "local";
        final var uri = URI.create("http://localhost:8080");

        appendServiceUrlOperation.execute(path, name, uri);
    }
}
