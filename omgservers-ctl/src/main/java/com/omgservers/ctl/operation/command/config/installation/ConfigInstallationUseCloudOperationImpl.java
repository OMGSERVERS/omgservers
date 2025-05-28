package com.omgservers.ctl.operation.command.config.installation;

import com.omgservers.ctl.dto.region.RegionEnum;
import com.omgservers.ctl.operation.wal.installation.AppendInstallationDetailsOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ConfigInstallationUseCloudOperationImpl implements ConfigInstallationUseCloudOperation {

    final AppendInstallationDetailsOperation appendInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final RegionEnum region) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var name = region.getRegion();
        final var api = region.getApi();
        final var registry = region.getRegistry();

        appendInstallationDetailsOperation.execute(path, name, api, registry);
    }
}
