package com.omgservers.module.runtime.impl.operation.deleteRuntime;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.module.runtime.impl.operation.selectRuntime.SelectRuntimeOperation;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
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

    final ChangeObjectOperation changeObjectOperation;
    final SelectRuntimeOperation selectRuntimeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntime(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long id) {
        return selectRuntimeOperation.selectRuntime(sqlConnection, shard, id)
                .flatMap(runtime -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_runtime
                                where id = $1
                                """,
                        Collections.singletonList(id),
                        () -> new RuntimeDeletedEventBodyModel(runtime),
                        () -> logModelFactory.create("Runtime was deleted, runtime=" + runtime)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
