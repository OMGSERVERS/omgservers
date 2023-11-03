package com.omgservers.module.system.impl.operation.deleteContainer;

import com.omgservers.model.event.body.ContainerDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
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
class DeleteContainerOperationImpl implements DeleteContainerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteContainer(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """                        
                        update system.tab_container
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ContainerDeletedEventBodyModel(id),
                () -> logModelFactory.create("Container was deleted, id=" + id)
        );
    }
}
