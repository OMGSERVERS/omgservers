package com.omgservers.service.module.system.impl.operation.deleteJob;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.system.impl.operation.selectJobByShardKeyAndEntityIdAndQualifierOperation.SelectJobByShardKeyAndEntityIdAndQualifierOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
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

    final SelectJobByShardKeyAndEntityIdAndQualifierOperation selectJobByShardKeyAndEntityIdAndQualifierOperation;
    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteJob(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final Long shardKey,
                                  final Long entityId,
                                  final JobQualifierEnum qualifier) {
        return selectJobByShardKeyAndEntityIdAndQualifierOperation.selectJobByShardKeyAndEntityIdAndQualifier(
                        sqlConnection,
                        shardKey,
                        entityId,
                        qualifier)
                .flatMap(job -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, 0,
                        """
                                delete from system.tab_job
                                where shard_key = $1 and entity_id = $2 and qualifier = $3
                                """,
                        Arrays.asList(shardKey, entityId, qualifier),
                        () -> new JobDeletedEventBodyModel(job),
                        () -> logModelFactory.create("Job was deleted, job=" + job)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
