package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.SupportClient;
import com.omgservers.ctl.configuration.LocalTestingConfiguration;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLocalTestingSupportClientOperationImpl implements CreateLocalTestingSupportClientOperation {

    final CreateSupportAnonymousClientOperation createSupportAnonymousClientOperation;
    final CreateSupportClientOperation createSupportClientOperation;

    @Override
    public SupportClient execute() {
        final var serviceUri = LocalTestingConfiguration.LOCALTESTING_URI;
        final var supportUser = LocalTestingConfiguration.SUPPORT_USER_DEFAULT_ALIAS;
        final var supportPassword = LocalTestingConfiguration.SUPPORT_USER_DEFAULT_PASSWORD;

        final var supportToken = supportCreateToken(serviceUri, supportUser, supportPassword);
        final var supportClient = createSupportClientOperation.execute(serviceUri, supportToken);

        return supportClient;
    }

    String supportCreateToken(final URI serviceUri,
                              final String supportUser,
                              final String supportPassword) {
        final var supportAnonymousClient = createSupportAnonymousClientOperation.execute(serviceUri);
        final var request = new CreateTokenSupportRequest(supportUser, supportPassword);
        return supportAnonymousClient.execute(request)
                .map(CreateTokenSupportResponse::getRawToken)
                .await().indefinitely();
    }
}
