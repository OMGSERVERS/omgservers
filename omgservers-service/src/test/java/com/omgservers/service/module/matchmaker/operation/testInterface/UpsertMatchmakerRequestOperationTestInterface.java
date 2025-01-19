package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.UpsertMatchmakerRequestOperation;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertMatchmakerRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerRequestOperation upsertRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmakerRequest(final int shard,
                                                          final MatchmakerRequestModel matchmakerRequest) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRequestOperation
                                    .execute(changeContext, sqlConnection, shard, matchmakerRequest))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
