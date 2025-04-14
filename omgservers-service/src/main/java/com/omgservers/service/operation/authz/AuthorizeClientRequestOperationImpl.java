package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizeClientRequestOperationImpl implements AuthorizeClientRequestOperation {

    final ClientShard clientShard;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    @Override
    public Uni<ClientAuthorization> execute(final Long clientId,
                                            final Long userId) {
        return getClient(clientId)
                .map(client -> {
                    final var clientUserId = client.getUserId();
                    if (clientUserId.equals(userId)) {
                        return new ClientAuthorization(clientId, userId);
                    } else {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.UNAUTHORIZED_REQUEST,
                                String.format("unauthorized request, clientId=%d, userId=%d", clientId, userId));
                    }
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }
}
