package com.omgservers.ctl.operation.command.support.tenant;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportTenantCreateTenantOperationImpl implements SupportTenantCreateTenantOperation {

    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final CreateSupportClientOperation createSupportClientOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String service) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetails = findInstallationDetailsOperation.execute(wal, service);
        final var installationName = installationDetails.getName();
        final var installationUri = installationDetails.getAddress();

        final var supportTokenLog = findSupportTokenOperation.execute(wal, installationName);
        final var supportToken = supportTokenLog.getToken();
        final var supportClient = createSupportClientOperation.execute(installationUri, supportToken);

        final var request = new CreateTenantSupportRequest();
        final var tenantId = supportClient.execute(request)
                .map(CreateTenantSupportResponse::getId)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.TENANT_ID, tenantId.toString());
    }
}
