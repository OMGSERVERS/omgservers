package com.omgservers.service.module.matchmaker.impl.operation.upsertRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.request.RequestModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRequestOperationImpl implements UpsertRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRequest(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final RequestModel request) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_request(
                            id, matchmaker_id, created, modified, user_id, client_id, mode, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        request.getId(),
                        request.getMatchmakerId(),
                        request.getCreated().atOffset(ZoneOffset.UTC),
                        request.getModified().atOffset(ZoneOffset.UTC),
                        request.getUserId(),
                        request.getClientId(),
                        request.getMode(),
                        getConfigString(request),
                        request.getDeleted()
                ),
                () -> null,
                () -> logModelFactory.create("Request was inserted, request=" + request)
        );
    }

    String getConfigString(RequestModel request) {
        try {
            return objectMapper.writeValueAsString(request.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
