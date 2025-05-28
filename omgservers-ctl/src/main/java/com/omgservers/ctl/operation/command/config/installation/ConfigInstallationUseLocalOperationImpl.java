package com.omgservers.ctl.operation.command.config.installation;

import com.omgservers.ctl.configuration.LocalConfiguration;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.AppendInstallationDetailsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ConfigInstallationUseLocalOperationImpl implements ConfigInstallationUseLocalOperation {

    final AppendInstallationDetailsOperation appendInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute() {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var name = LocalConfiguration.SERVICE_NAME;
        final var api = LocalConfiguration.API_URI;
        final var registry = LocalConfiguration.REGISTRY_URI;

        appendInstallationDetailsOperation.execute(path, name, api, registry);
    }
}
