package com.omgservers.module.script.impl.operation.deleteScript;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.ScriptDeletedEventBodyModel;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteScriptOperationImpl implements DeleteScriptOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final SelectScriptOperation selectScriptOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteScript(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return selectScriptOperation.selectScript(sqlConnection, shard, id)
                .flatMap(script -> executeChangeObjectOperation.executeChangeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_script
                                where id = $1
                                """,
                        Collections.singletonList(id),
                        () -> new ScriptDeletedEventBodyModel(script),
                        () -> logModelFactory.create("Script was deleted, script=" + script)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
