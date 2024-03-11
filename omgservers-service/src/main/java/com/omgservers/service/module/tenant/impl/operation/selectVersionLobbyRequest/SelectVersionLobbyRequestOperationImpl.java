package com.omgservers.service.module.tenant.impl.operation.selectVersionLobbyRequest;

import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionLobbyRequestModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionLobbyRequestOperationImpl implements SelectVersionLobbyRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionLobbyRequestModelMapper versionLobbyRequestModelMapper;

    @Override
    public Uni<VersionLobbyRequestModel> selectVersionLobbyRequest(final SqlConnection sqlConnection,
                                                                   final int shard,
                                                                   final Long tenantId,
                                                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_version_lobby_request
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Version lobby request",
                versionLobbyRequestModelMapper::fromRow);
    }
}
