package com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.getServiceAccountMethod;

import com.omgservers.base.impl.operation.selectServiceAccountOperation.SelectServiceAccountOperation;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetServiceAccountMethodImpl implements GetServiceAccountMethod {

    final SelectServiceAccountOperation getServiceAccountOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request) {
        GetServiceAccountHelpRequest.validate(request);

        final var username = request.getUsername();
        return pgPool.withTransaction(sqlConnection -> getServiceAccountOperation
                        .selectServiceAccount(sqlConnection, username))
                .map(GetServiceAccountHelpResponse::new);
    }
}
