package com.omgservers.service.shard.runtime.impl.operation.runtimeMessage;

import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeMessagesByIdsOperationImpl implements DeleteRuntimeMessagesByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Long runtimeId,
                                final List<Long> ids) {
        return changeObjectOperation.execute(changeContext, sqlConnection, shard,
                """
                        update $shard.tab_runtime_message
                        set modified = $3, deleted = true
                        where runtime_id = $1 and id = any($2) and deleted = false
                        """,
                List.of(
                        runtimeId,
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> null
        );
    }
}
