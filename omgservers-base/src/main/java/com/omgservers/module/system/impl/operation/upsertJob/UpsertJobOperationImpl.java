package com.omgservers.module.system.impl.operation.upsertJob;

import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertJob(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final JobModel job) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into internal.tab_job(id, created, shard_key, entity, type)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        job.getId(),
                        job.getCreated().atOffset(ZoneOffset.UTC),
                        job.getShardKey(),
                        job.getEntity(),
                        job.getType()
                ),
                () -> new JobCreatedEventBodyModel(job.getShardKey(), job.getEntity(), job.getType()),
                () -> logModelFactory.create("Job was created, job=" + job)
        );
    }
}
