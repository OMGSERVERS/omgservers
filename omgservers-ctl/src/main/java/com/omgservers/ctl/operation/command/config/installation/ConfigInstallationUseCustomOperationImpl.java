package com.omgservers.ctl.operation.command.config.installation;

import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.AppendInstallationDetailsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;

@ApplicationScoped
@AllArgsConstructor
public class ConfigInstallationUseCustomOperationImpl implements ConfigInstallationUseCustomOperation {

    final AppendInstallationDetailsOperation appendInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String name,
                        final URI api,
                        final URI registry) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        appendInstallationDetailsOperation.execute(path, name, api, registry);
    }
}
