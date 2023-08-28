package com.omgservers.module.user.impl.service.userInternalService.impl.method.syncUser;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
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
    public Uni<SyncUserShardedResponse> syncUser(final SyncUserShardedRequest request) {
        SyncUserShardedRequest.validate(request);

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
                .map(SyncUserShardedResponse::new);
    }
}
