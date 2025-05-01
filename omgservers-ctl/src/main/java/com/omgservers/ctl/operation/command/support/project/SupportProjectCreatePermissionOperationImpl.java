package com.omgservers.ctl.operation.command.support.project;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.service.FindServiceUrlOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportProjectCreatePermissionOperationImpl implements SupportProjectCreatePermissionOperation {

    final CreateSupportClientOperation createSupportClientOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final FindServiceUrlOperation findServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final Long userId,
                        final ProjectPermissionEnum permission,
                        final String service,
                        final String user) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrlLog = findServiceUrlOperation.execute(wal, service);
        final var serviceName = serviceUrlLog.getName();
        final var serviceUri = serviceUrlLog.getUri();

        final var supportTokenLog = findSupportTokenOperation.execute(wal, serviceName, user);
        final var supportToken = supportTokenLog.getToken();
        final var supportClient = createSupportClientOperation.execute(serviceUri, supportToken);

        final var qualifier = permission.toQualifier();
        final var request = new CreateTenantProjectPermissionsSupportRequest(tenant,
                project,
                userId,
                Collections.singleton(qualifier));
        final var result = supportClient.execute(request)
                .map(CreateTenantProjectPermissionsSupportResponse::getCreatedPermissions)
                .map(List::size)
                .map(size -> size > 0)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, result.toString());
    }
}
