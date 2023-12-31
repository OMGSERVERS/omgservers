package com.omgservers.service.module.tenant.impl.operation.upsertVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertVersionOperationImpl implements UpsertVersionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertVersion(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final VersionModel version) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version(
                            id, tenant_id, stage_id, created, modified, config, source_code, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        version.getId(),
                        version.getTenantId(),
                        version.getStageId(),
                        version.getCreated().atOffset(ZoneOffset.UTC),
                        version.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(version),
                        getSourceCode(version),
                        version.getDeleted()
                ),
                () -> new VersionCreatedEventBodyModel(version.getTenantId(), version.getId()),
                () -> logModelFactory.create("Version was created, " + version)
        );
    }

    String getConfigString(VersionModel version) {
        try {
            return objectMapper.writeValueAsString(version.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }

    String getSourceCode(VersionModel version) {
        try {
            return objectMapper.writeValueAsString(version.getSourceCode());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
