package com.omgservers.connector.operation;

import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateConnectorTokenOperationImpl implements CreateConnectorTokenOperation {

    final CreateServiceAnonymousClientOperation createServiceAnonymousClientOperation;
    final GetConnectorConfigOperation getConnectorConfigOperation;

    @Override
    public Uni<String> execute() {
        final var userAlias = getConnectorConfigOperation.getConnectorConfig().user().alias();
        final var userPassword = getConnectorConfigOperation.getConnectorConfig().user().password();

        final var serviceUri = getConnectorConfigOperation.getConnectorConfig().serviceUri();
        final var serviceClient = createServiceAnonymousClientOperation.execute(serviceUri);
        final var createTokenConnectorRequest = new CreateTokenConnectorRequest(userAlias, userPassword);

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> serviceClient.execute(createTokenConnectorRequest))
                .map(CreateTokenConnectorResponse::getRawToken);
    }
}
