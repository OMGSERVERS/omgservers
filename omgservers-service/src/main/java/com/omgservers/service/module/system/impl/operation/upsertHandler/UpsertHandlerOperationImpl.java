package com.omgservers.service.module.system.impl.operation.upsertHandler;

import com.omgservers.model.event.body.HandlerCreatedEventBodyModel;
import com.omgservers.model.handler.HandlerModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertHandlerOperationImpl implements UpsertHandlerOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertHandler(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final HandlerModel handler) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into system.tab_handler(
                            id, created, modified, deleted)
                        values($1, $2, $3, $4)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        handler.getId(),
                        handler.getCreated().atOffset(ZoneOffset.UTC),
                        handler.getModified().atOffset(ZoneOffset.UTC),
                        handler.getDeleted()
                ),
                () -> new HandlerCreatedEventBodyModel(handler.getId()),
                () -> null
        );
    }
}
