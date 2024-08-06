package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.upsertVersionLobbyRequest.UpsertVersionLobbyRequestOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertVersionLobbyRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertVersionLobbyRequestOperation upsertVersionLobbyRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionLobbyRequest(final int shard,
                                                            final VersionLobbyRequestModel versionLobbyRequest) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertVersionLobbyRequestOperation
                                    .upsertVersionLobbyRequest(changeContext,
                                            sqlConnection,
                                            shard,
                                            versionLobbyRequest))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
