package com.omgservers.service.server.index.operation;

import com.omgservers.service.event.body.system.IndexDeletedEventBodyModel;
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
class DeleteIndexOperationImpl implements DeleteIndexOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteIndex(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final Long id) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection,
                """
                        update $server.tab_index
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                List.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new IndexDeletedEventBodyModel(id),
                () -> null
        );
    }
}
