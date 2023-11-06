package com.omgservers.service.operation.testInterface;

import com.omgservers.model.job.JobModel;
import com.omgservers.service.module.system.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertJobOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertJobOperation upsertJobOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertJob(final JobModel job) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertJobOperation
                                    .upsertJob(changeContext, sqlConnection, job))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
