package com.omgservers.service.module.tenant.impl.operation.upsertVersionMatchmakerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

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
        final var tenantId = versionMatchmakerRequest.getTenantId();
        final var id = versionMatchmakerRequest.getId();

        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_matchmaker_request(
                            id, tenant_id, version_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        id,
                        tenantId,
                        versionMatchmakerRequest.getVersionId(),
                        versionMatchmakerRequest.getCreated().atOffset(ZoneOffset.UTC),
                        versionMatchmakerRequest.getModified().atOffset(ZoneOffset.UTC),
                        versionMatchmakerRequest.getMatchmakerId(),
                        versionMatchmakerRequest.getDeleted()
                ),
                () -> new VersionMatchmakerRequestCreatedEventBodyModel(tenantId, id),
                () -> null
        );
    }
}