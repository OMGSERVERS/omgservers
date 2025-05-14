package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final MatchmakerRequestModel matchmakerRequest) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_matchmaker_request(
                            id, idempotency_key, matchmaker_id, created, modified, client_id, mode, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerRequest.getId(),
                        matchmakerRequest.getIdempotencyKey(),
                        matchmakerRequest.getMatchmakerId(),
                        matchmakerRequest.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerRequest.getModified().atOffset(ZoneOffset.UTC),
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
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
