package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectVersionLobbyRefByLobbyId;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionLobbyRefModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionLobbyRefByLobbyIdOperationImpl implements SelectVersionLobbyRefByLobbyIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionLobbyRefModelMapper versionLobbyRefModelMapper;

    @Override
    public Uni<VersionLobbyRefModel> selectVersionLobbyRefByLobbyId(final SqlConnection sqlConnection,
                                                                    final int shard,
                                                                    final Long tenantId,
                                                                    final Long versionId,
                                                                    final Long lobbyId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_version_lobby_ref
                        where tenant_id = $1 and version_id = $2 and lobby_id = $3
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, versionId, lobbyId),
                "Version lobby ref",
                versionLobbyRefModelMapper::fromRow);
    }
}
