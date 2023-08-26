package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod;

import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.userModule.SyncUserShardRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncUserMethodImpl implements SyncUserMethod {

    final InternalModule internalModule;

    final UpsertUserOperation upsertUserOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserShardRequest request) {
        SyncUserShardRequest.validate(request);

        final var user = request.getUser();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> upsertUserOperation
                                .upsertUser(sqlConnection, shardModel.shard(), user),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("User was created, user=" + user);
                            } else {
                                return logModelFactory.create("User was updated, user=" + user);
                            }
                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncUserInternalResponse::new);
    }
}
