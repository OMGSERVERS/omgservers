package com.omgservers.ctl.operation.command.local;

import com.omgservers.ctl.client.SupportClient;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateLocalSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.local.FindLocalTenantOperation;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperLocalDeleteTenantOperationImpl implements DeveloperLocalDeleteTenantOperation {

    final CreateLocalSupportClientOperation createLocalSupportClientOperation;
    final FindLocalTenantOperation findLocalTenantOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String stage) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var supportClient = createLocalSupportClientOperation.execute();

        final var localTenantLog = findLocalTenantOperation.execute(wal, tenant, project, stage);

        final var tenantDeleted = deleteTenant(supportClient, localTenantLog.getTenant());
        final var developerDeleted = deleteDeveloper(supportClient, localTenantLog.getDeveloper());

        appendResultMapOperation.execute(path, KeyEnum.RESULT, Boolean.valueOf(tenantDeleted || developerDeleted)
                .toString());
    }

    boolean deleteTenant(final SupportClient supportClient, final String tenant) {
        final var request = new DeleteTenantSupportRequest(tenant);
        return supportClient.execute(request)
                .map(DeleteTenantSupportResponse::getDeleted)
                .await().indefinitely();
    }

    boolean deleteDeveloper(final SupportClient supportClient, final String user) {
        final var request = new DeleteDeveloperSupportRequest(Long.valueOf(user));
        return supportClient.execute(request)
                .map(DeleteDeveloperSupportResponse::getDeleted)
                .await().indefinitely();
    }
}
