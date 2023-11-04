package com.omgservers.service.module.system.impl.operation.deleteIndex;

import com.omgservers.model.event.body.IndexDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

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
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        delete from system.tab_index
                        where id = $1
                        """,
                Collections.singletonList(id),
                () -> new IndexDeletedEventBodyModel(id),
                () -> logModelFactory.create("Index was deleted, id=" + id)
        );
    }
}
