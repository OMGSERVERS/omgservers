package com.omgservers.ctl.operation.command.support.developer;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.service.FindServiceUrlOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportDeveloperCreateDeveloperOperationImpl implements SupportDeveloperCreateDeveloperOperation {

    final CreateSupportClientOperation createSupportClientOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindServiceUrlOperation findServiceUrlOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String service, final String user) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrl = findServiceUrlOperation.execute(wal, service);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getUri();

        final var supportTokenLog = findSupportTokenOperation.execute(wal, serviceName, user);
        final var supportToken = supportTokenLog.getToken();
        final var supportClient = createSupportClientOperation.execute(serviceUri, supportToken);

        final var request = new CreateDeveloperSupportRequest();
        final var createDeveloperSupportResponse = supportClient.execute(request)
                .await().indefinitely();

        final var userId = createDeveloperSupportResponse.getUserId();
        final var password = createDeveloperSupportResponse.getPassword();

        appendResultMapOperation.execute(path, Map.of(
                KeyEnum.USER_ID, userId.toString(),
                KeyEnum.PASSWORD, password));
    }
}
