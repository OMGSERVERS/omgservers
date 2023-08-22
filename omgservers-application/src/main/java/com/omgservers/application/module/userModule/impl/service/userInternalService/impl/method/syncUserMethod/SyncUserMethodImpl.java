package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.SyncUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.SyncUserInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserInternalRequest request) {
        SyncUserInternalRequest.validate(request);

        final var user = request.getUser();
        return changeOperation.changeWithLog(request,
                        (sqlConnection, shardModel) -> upsertUserOperation
                                .upsertUser(sqlConnection, shardModel.shard(), user),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("User was created, user=" + user);
                            } else {
                                return logModelFactory.create("User was updated, user=" + user);
                            }
                        }
                )
                .map(SyncUserInternalResponse::new);
    }
}
