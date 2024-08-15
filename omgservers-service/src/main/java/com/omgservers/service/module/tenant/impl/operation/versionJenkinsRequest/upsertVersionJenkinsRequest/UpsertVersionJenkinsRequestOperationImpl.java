package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.upsertVersionJenkinsRequest;

import com.omgservers.service.event.body.module.tenant.VersionJenkinsRequestCreatedEventBodyModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
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
class UpsertVersionJenkinsRequestOperationImpl implements UpsertVersionJenkinsRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertVersionJenkinsRequest(final ChangeContext<?> changeContext,
                                                    final SqlConnection sqlConnection,
                                                    final int shard,
                                                    final VersionJenkinsRequestModel versionJenkinsRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_jenkins_request(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        versionJenkinsRequest.getId(),
                        versionJenkinsRequest.getIdempotencyKey(),
                        versionJenkinsRequest.getTenantId(),
                        versionJenkinsRequest.getVersionId(),
                        versionJenkinsRequest.getCreated().atOffset(ZoneOffset.UTC),
                        versionJenkinsRequest.getModified().atOffset(ZoneOffset.UTC),
                        versionJenkinsRequest.getQualifier(),
                        versionJenkinsRequest.getBuildNumber(),
                        versionJenkinsRequest.getDeleted()
                ),
                () -> new VersionJenkinsRequestCreatedEventBodyModel(versionJenkinsRequest.getTenantId(),
                        versionJenkinsRequest.getId()),
                () -> null
        );
    }
}
