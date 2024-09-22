package com.omgservers.service.module.tenant.impl.operation.tenantVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.event.body.module.tenant.TenantVersionCreatedEventBodyModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
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
import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertTenantVersionOperationImpl implements UpsertTenantVersionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantVersionModel tenantVersion) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version(
                            id, idempotency_key, tenant_id, project_id, created, modified, config, archive, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantVersion.getId(),
                        tenantVersion.getIdempotencyKey(),
                        tenantVersion.getTenantId(),
                        tenantVersion.getProjectId(),
                        tenantVersion.getCreated().atOffset(ZoneOffset.UTC),
                        tenantVersion.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(tenantVersion),
                        getArchiveBytes(tenantVersion),
                        tenantVersion.getDeleted()
                ),
                () -> new TenantVersionCreatedEventBodyModel(tenantVersion.getTenantId(), tenantVersion.getId()),
                () -> null
        );
    }

    String getConfigString(final TenantVersionModel version) {
        try {
            return objectMapper.writeValueAsString(version.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }

    byte[] getArchiveBytes(final TenantVersionModel version) {
        return Base64.getDecoder().decode(version.getBase64Archive());
    }
}