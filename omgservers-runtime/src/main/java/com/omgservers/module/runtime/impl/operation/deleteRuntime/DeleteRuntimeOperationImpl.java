package com.omgservers.module.runtime.impl.operation.deleteRuntime;

import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.module.runtime.impl.operation.selectRuntime.SelectRuntimeOperation;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeOperationImpl implements DeleteRuntimeOperation {

    final SelectRuntimeOperation selectRuntimeOperation;
    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntime(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long id) {
        return changeObjectOperation.changeObject(changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime
                        set deleted = true
                        where id = $1 and deleted = false
                        """,
                Collections.singletonList(id),
                () -> new RuntimeDeletedEventBodyModel(id),
                () -> logModelFactory.create("Runtime was deleted, id=" + id)
        );
    }
}
