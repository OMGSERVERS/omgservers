package com.omgservers.module.system.impl.operation.upsertJob;

import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
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
class UpsertJobOperationImpl implements UpsertJobOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertJob(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final JobModel job) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into system.tab_job(id, created, shard_key, entity_id, qualifier)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        job.getId(),
                        job.getCreated().atOffset(ZoneOffset.UTC),
                        job.getShardKey(),
                        job.getEntityId(),
                        job.getQualifier()
                ),
                () -> new JobCreatedEventBodyModel(job.getShardKey(), job.getEntityId(), job.getQualifier()),
                () -> logModelFactory.create("Job was created, job=" + job)
        );
    }
}
