package com.omgservers.service.operation.testInterface;

import com.omgservers.service.module.system.impl.operation.deleteJob.DeleteJobOperation;
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
public class DeleteJobOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteJobOperation deleteJobOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteJob(final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteJobOperation
                                    .deleteJob(changeContext, sqlConnection, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
