package com.omgservers.service.server.service.job.operation.upsertJob;

import com.omgservers.schema.event.body.system.JobCreatedEventBodyModel;
import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

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
                            id, idempotency_key, created, modified, qualifier, entity_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        job.getId(),
                        job.getIdempotencyKey(),
                        job.getCreated().atOffset(ZoneOffset.UTC),
                        job.getModified().atOffset(ZoneOffset.UTC),
                        job.getQualifier(),
                        job.getEntityId(),
                        job.getDeleted()
                ),
                () -> new JobCreatedEventBodyModel(job.getId()),
                () -> null
        );
    }
}
