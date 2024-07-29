package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.selectVersionLobbyRequestByLobbyId;

import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionLobbyRequestModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionLobbyRequestByLobbyIdOperationImpl implements SelectVersionLobbyRequestByLobbyIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionLobbyRequestModelMapper versionLobbyRequestModelMapper;

    @Override
    public Uni<VersionLobbyRequestModel> selectVersionLobbyRequestByLobbyId(final SqlConnection sqlConnection,
                                                                            final int shard,
                                                                            final Long tenantId,
                                                                            final Long versionId,
                                                                            final Long lobbyId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_version_lobby_request
                        where tenant_id = $1 and version_id = $2 and lobby_id = $3
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, versionId, lobbyId),
                "Version lobby request",
                versionLobbyRequestModelMapper::fromRow);
    }
}
