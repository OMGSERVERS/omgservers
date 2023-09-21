package com.omgservers.module.system.impl.operation.deleteJob;

import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobOperationImpl implements DeleteJobOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteJob(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final Long shardKey,
                                  final Long entityId) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, 0,
                """
                        delete from system.tab_job
                        where shard_key = $1 and entity_id = $2
                        """,
                Arrays.asList(shardKey, entityId),
                () -> new JobDeletedEventBodyModel(shardKey, entityId),
                () -> logModelFactory.create(String.format("Job was deleted, " +
                        "shardKey=%s, entityId=%s", shardKey, entityId))
        );
    }
}
