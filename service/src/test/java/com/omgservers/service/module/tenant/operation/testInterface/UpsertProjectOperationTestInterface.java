package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.module.tenant.impl.operation.project.upsertProject.UpsertProjectOperation;
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
public class UpsertProjectOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertProjectOperation upsertProjectOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertProject(final int shard,
                                                final ProjectModel project) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertProjectOperation
                                    .upsertProject(changeContext, sqlConnection, shard, project))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
