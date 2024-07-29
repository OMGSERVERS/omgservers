package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.upsertVersionMatchmakerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertVersionMatchmakerRequestOperationImpl implements UpsertVersionMatchmakerRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertVersionMatchmakerRequest(final ChangeContext<?> changeContext,
                                                       final SqlConnection sqlConnection,
                                                       final int shard,
                                                       final VersionMatchmakerRequestModel versionMatchmakerRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_matchmaker_request(
                            id, idempotency_key, tenant_id, version_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        versionMatchmakerRequest.getId(),
                        versionMatchmakerRequest.getIdempotencyKey(),
                        versionMatchmakerRequest.getTenantId(),
                        versionMatchmakerRequest.getVersionId(),
                        versionMatchmakerRequest.getCreated().atOffset(ZoneOffset.UTC),
                        versionMatchmakerRequest.getModified().atOffset(ZoneOffset.UTC),
                        versionMatchmakerRequest.getMatchmakerId(),
                        versionMatchmakerRequest.getDeleted()
                ),
                () -> new VersionMatchmakerRequestCreatedEventBodyModel(versionMatchmakerRequest.getTenantId(),
                        versionMatchmakerRequest.getId()),
                () -> null
        );
    }
}
