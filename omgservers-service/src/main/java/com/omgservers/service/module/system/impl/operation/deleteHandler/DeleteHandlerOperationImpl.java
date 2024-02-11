package com.omgservers.service.module.system.impl.operation.deleteHandler;

import com.omgservers.model.event.body.HandlerDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteHandlerOperationImpl implements DeleteHandlerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteHandler(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update system.tab_handler
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new HandlerDeletedEventBodyModel(id),
                () -> null
        );
    }
}
