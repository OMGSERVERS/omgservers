package com.omgservers.service.module.system.impl.operation.upsertJob;

import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
                        insert into system.tab_job(
                            id, created, modified, shard_key, entity_id, qualifier, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        job.getId(),
                        job.getCreated().atOffset(ZoneOffset.UTC),
                        job.getModified().atOffset(ZoneOffset.UTC),
                        job.getShardKey(),
                        job.getEntityId(),
                        job.getQualifier(),
                        job.getDeleted()
                ),
                () -> new JobCreatedEventBodyModel(job.getId()),
                () -> logModelFactory.create("Job was created, job=" + job)
        );
    }
}
