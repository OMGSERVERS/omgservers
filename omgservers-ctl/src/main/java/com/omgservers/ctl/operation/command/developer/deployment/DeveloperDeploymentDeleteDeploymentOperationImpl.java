package com.omgservers.ctl.operation.command.developer.deployment;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.service.FindServiceUrlOperation;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperDeploymentDeleteDeploymentOperationImpl implements DeveloperDeploymentDeleteDeploymentOperation {

    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindServiceUrlOperation findServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final Long deploymentId,
                        final String service,
                        final String user) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrl = findServiceUrlOperation.execute(wal, service);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getUri();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, serviceName, user);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(serviceUri, developerToken);

        final var request = new DeleteDeploymentDeveloperRequest(deploymentId);
        final var deleted = developerClient.execute(request)
                .map(DeleteDeploymentDeveloperResponse::getDeleted)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, deleted.toString());
    }
}
