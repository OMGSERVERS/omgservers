package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.upsertVersionLobbyRef.UpsertVersionLobbyRefOperation;
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
public class UpsertVersionRuntimeOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertVersionLobbyRefOperation upsertVersionLobbyRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionRuntime(final int shard,
                                                       final VersionLobbyRefModel versionRuntime) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertVersionLobbyRefOperation
                                    .upsertVersionLobbyRef(changeContext, sqlConnection, shard, versionRuntime))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
