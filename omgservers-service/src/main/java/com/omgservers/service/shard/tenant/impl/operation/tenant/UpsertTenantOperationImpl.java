package com.omgservers.service.shard.tenant.impl.operation.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantOperationImpl implements UpsertTenantOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final TenantModel tenant) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_tenant(
                            id, idempotency_key, created, modified, config, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenant.getId(),
                        tenant.getIdempotencyKey(),
                        tenant.getCreated().atOffset(ZoneOffset.UTC),
                        tenant.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(tenant),
                        tenant.getDeleted()
                ),
                () -> new TenantCreatedEventBodyModel(tenant.getId()),
                () -> null
        );
    }

    String getConfigString(final TenantModel tenant) {
        try {
            return objectMapper.writeValueAsString(tenant.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
