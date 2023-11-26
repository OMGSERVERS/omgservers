package com.omgservers.service.module.tenant.impl.operation.upsertVersionMatchmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.VersionMatchmakerCreatedEventBodyModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
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
class UpsertVersionMatchmakerOperationImpl implements UpsertVersionMatchmakerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertVersionMatchmaker(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final VersionMatchmakerModel versionMatchmaker) {
        final var tenantId = versionMatchmaker.getTenantId();
        final var id = versionMatchmaker.getId();

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
                        versionMatchmaker.getVersionId(),
                        versionMatchmaker.getCreated().atOffset(ZoneOffset.UTC),
                        versionMatchmaker.getModified().atOffset(ZoneOffset.UTC),
                        versionMatchmaker.getMatchmakerId(),
                        versionMatchmaker.getDeleted()
                ),
                () -> new VersionMatchmakerCreatedEventBodyModel(tenantId, id),
                () -> logModelFactory.create(String.format("Version matchmaker was created, " +
                        "tenantId=%d, id=%d", tenantId, id))
        );
    }
}
