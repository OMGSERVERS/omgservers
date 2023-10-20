package com.omgservers.module.tenant.impl.operation.upsertVersionMatchmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.StageMatchmakerCreatedEventBodyModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
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
class UpsertVersionMatchmakerOperationImpl implements UpsertVersionMatchmakerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertStageMatchmaker(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final VersionMatchmakerModel stageMatchmaker) {
        final var tenantId = stageMatchmaker.getTenantId();
        final var id = stageMatchmaker.getId();

        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_matchmaker(
                            id, tenant_id, version_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        id,
                        tenantId,
                        stageMatchmaker.getVersionId(),
                        stageMatchmaker.getCreated().atOffset(ZoneOffset.UTC),
                        stageMatchmaker.getModified().atOffset(ZoneOffset.UTC),
                        stageMatchmaker.getMatchmakerId(),
                        stageMatchmaker.getDeleted()
                ),
                () -> new StageMatchmakerCreatedEventBodyModel(tenantId, id),
                () -> logModelFactory.create(String.format("Version matchmaker was created, " +
                        "tenantId=%d, id=%d", tenantId, id))
        );
    }
}
