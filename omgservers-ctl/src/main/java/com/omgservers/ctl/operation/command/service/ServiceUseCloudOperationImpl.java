package com.omgservers.ctl.operation.command.service;

import com.omgservers.ctl.dto.region.RegionEnum;
import com.omgservers.ctl.operation.wal.service.AppendServiceUrlOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ServiceUseCloudOperationImpl implements ServiceUseCloudOperation {

    final AppendServiceUrlOperation appendServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final RegionEnum region) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var name = region.getRegion();
        final var uri = region.getUri();

        appendServiceUrlOperation.execute(path, name, uri);
    }
}
