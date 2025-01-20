package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRequest.testInterface;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRequest.UpsertTenantMatchmakerRequestOperation;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertTenantMatchmakerRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantMatchmakerRequestOperation upsertTenantMatchmakerRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantMatchmakerRequestModel tenantMatchmakerRequest) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantMatchmakerRequestOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            0,
                                            tenantMatchmakerRequest))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
