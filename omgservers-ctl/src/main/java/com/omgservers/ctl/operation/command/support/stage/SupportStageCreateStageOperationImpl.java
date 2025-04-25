package com.omgservers.ctl.operation.command.support.stage;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.service.FindServiceUrlOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportStageCreateStageOperationImpl implements SupportStageCreateStageOperation {

    final CreateSupportClientOperation createSupportClientOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindServiceUrlOperation findServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
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

        final var request = new CreateTenantStageSupportRequest(tenant, project);
        final var stageId = supportClient.execute(request)
                .map(CreateTenantStageSupportResponse::getStageId)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.STAGE_ID, stageId.toString());
    }
}
