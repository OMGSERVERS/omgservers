package com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.service.event.body.module.tenant.TenantJenkinsRequestCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertTenantJenkinsRequestOperationImpl implements UpsertTenantJenkinsRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantJenkinsRequestModel tenantJenkinsRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_jenkins_request(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantJenkinsRequest.getId(),
                        tenantJenkinsRequest.getIdempotencyKey(),
                        tenantJenkinsRequest.getTenantId(),
                        tenantJenkinsRequest.getVersionId(),
                        tenantJenkinsRequest.getCreated().atOffset(ZoneOffset.UTC),
                        tenantJenkinsRequest.getModified().atOffset(ZoneOffset.UTC),
                        tenantJenkinsRequest.getQualifier(),
                        tenantJenkinsRequest.getBuildNumber(),
                        tenantJenkinsRequest.getDeleted()
                ),
                () -> new TenantJenkinsRequestCreatedEventBodyModel(tenantJenkinsRequest.getTenantId(),
                        tenantJenkinsRequest.getId()),
                () -> null
        );
    }
}
