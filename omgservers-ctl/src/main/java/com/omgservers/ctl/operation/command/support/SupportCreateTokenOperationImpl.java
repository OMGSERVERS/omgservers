package com.omgservers.ctl.operation.command.support;

import com.omgservers.ctl.operation.client.CreateSupportAnonymousClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.ctl.operation.wal.support.AppendSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportCreateTokenOperationImpl implements SupportCreateTokenOperation {

    final CreateSupportAnonymousClientOperation createSupportAnonymousClientOperation;
    final AppendSupportTokenOperation appendSupportTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String user,
                        final String password,
                        final String service) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrl = findInstallationDetailsOperation.execute(wal, service);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getApi();

        final var supportAnonymousClient = createSupportAnonymousClientOperation.execute(serviceUri);

        final var request = new CreateTokenSupportRequest(user, password);
        final var adminToken = supportAnonymousClient.execute(request)
                .map(CreateTokenSupportResponse::getRawToken)
                .await().indefinitely();

        appendSupportTokenOperation.execute(path, serviceName, user, adminToken);
    }
}
