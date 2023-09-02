package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.syncVersion;

import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.dto.tenant.SyncVersionShardedResponse;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertVersion.UpsertVersionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncVersionMethodImpl implements SyncVersionMethod {

    final InternalModule internalModule;

    final UpsertVersionOperation upsertVersionOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request) {
        SyncVersionShardedRequest.validate(request);

        final var version = request.getVersion();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertVersionOperation
                                .upsertVersion(sqlConnection, shardModel.shard(), version),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Version was created, version=" + version);
                            } else {
                                return null;
                            }
                        },
                        inserted -> {
                            final var tenantId = request.getTenantId();
                            final var id = version.getId();
                            if (inserted) {
                                return new VersionCreatedEventBodyModel(tenantId, id);
                            } else {
                                return null;
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncVersionShardedResponse::new);
    }
}
