package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.upsertMatchmakerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
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
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerRequestOperationImpl implements UpsertMatchmakerRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchmakerRequest(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final MatchmakerRequestModel matchmakerRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_request(
                            id, idempotency_key, matchmaker_id, created, modified, user_id, client_id, mode, config, 
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerRequest.getId(),
                        matchmakerRequest.getIdempotencyKey(),
                        matchmakerRequest.getMatchmakerId(),
                        matchmakerRequest.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerRequest.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerRequest.getUserId(),
                        matchmakerRequest.getClientId(),
                        matchmakerRequest.getMode(),
                        getConfigString(matchmakerRequest),
                        matchmakerRequest.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getConfigString(MatchmakerRequestModel matchmakerRequest) {
        try {
            return objectMapper.writeValueAsString(matchmakerRequest.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
