package com.omgservers.service.module.server.impl.operation.deleteServerContainer;

import com.omgservers.model.event.body.module.server.ServerContainerDeletedEventBodyModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class DeleteServerContainerOperationImpl implements DeleteServerContainerOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> deleteServerContainer(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long serverId,
                                              final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_server_container
                        set modified = $2, deleted = true
                        where server_id = $1 and id = $1 and deleted = false
                        """,
                List.of(
                        serverId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ServerContainerDeletedEventBodyModel(serverId, id),
                () -> null
        );
    }
}
