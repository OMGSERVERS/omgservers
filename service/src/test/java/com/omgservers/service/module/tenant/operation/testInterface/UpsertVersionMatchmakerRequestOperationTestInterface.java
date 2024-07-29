package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.upsertVersionMatchmakerRequest.UpsertVersionMatchmakerRequestOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertVersionMatchmakerRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertVersionMatchmakerRequestOperation upsertVersionMatchmakerRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionMatchmakerRequest(final int shard,
                                                                 final VersionMatchmakerRequestModel versionMatchmakerRequest) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertVersionMatchmakerRequestOperation
                                    .upsertVersionMatchmakerRequest(changeContext,
                                            sqlConnection,
                                            shard,
                                            versionMatchmakerRequest))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
