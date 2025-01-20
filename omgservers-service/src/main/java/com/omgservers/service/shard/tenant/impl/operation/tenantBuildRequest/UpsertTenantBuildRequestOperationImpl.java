package com.omgservers.service.shard.tenant.impl.operation.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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
class UpsertTenantBuildRequestOperationImpl implements UpsertTenantBuildRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantBuildRequestModel tenantBuildRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_build_request(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantBuildRequest.getId(),
                        tenantBuildRequest.getIdempotencyKey(),
                        tenantBuildRequest.getTenantId(),
                        tenantBuildRequest.getVersionId(),
                        tenantBuildRequest.getCreated().atOffset(ZoneOffset.UTC),
                        tenantBuildRequest.getModified().atOffset(ZoneOffset.UTC),
                        tenantBuildRequest.getQualifier(),
                        tenantBuildRequest.getBuildNumber(),
                        tenantBuildRequest.getDeleted()
                ),
                () -> new TenantBuildRequestCreatedEventBodyModel(tenantBuildRequest.getTenantId(),
                        tenantBuildRequest.getId()),
                () -> null
        );
    }
}
