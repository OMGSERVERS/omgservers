package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateTenantLobbyResourceStatusOperationImpl implements UpdateTenantLobbyResourceStatusOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final Long id,
                                final TenantLobbyResourceStatusEnum status) {
        return changeObjectOperation.changeObject(changeContext, sqlConnection, shard,
                """
                        update $schema.tab_tenant_lobby_resource
                        set modified = $3, status = $4
                        where tenant_id = $1 and id = $2
                        """,
                List.of(
                        tenantId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> null
        );
    }
}
