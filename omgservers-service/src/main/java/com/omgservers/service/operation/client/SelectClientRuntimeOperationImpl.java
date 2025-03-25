package com.omgservers.service.operation.client;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.client.clientRuntimeRef.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.ViewClientRuntimeRefsResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SelectClientRuntimeOperationImpl implements SelectClientRuntimeOperation {

    final ClientShard clientShard;

    @Override
    public Uni<Long> execute(final Long clientId) {
        return viewClientRuntimeRefs(clientId)
                .map(clientRuntimeRefs -> {
                    if (clientRuntimeRefs.isEmpty()) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                "runtime was not selected, clientId=" + clientId);
                    } else {
                        return clientRuntimeRefs.getLast().getRuntimeId();
                    }
                });
    }

    Uni<List<ClientRuntimeRefModel>> viewClientRuntimeRefs(final Long clientId) {
        final var request = new ViewClientRuntimeRefsRequest(clientId);
        return clientShard.getService().execute(request)
                .map(ViewClientRuntimeRefsResponse::getClientRuntimeRefs);
    }
}
