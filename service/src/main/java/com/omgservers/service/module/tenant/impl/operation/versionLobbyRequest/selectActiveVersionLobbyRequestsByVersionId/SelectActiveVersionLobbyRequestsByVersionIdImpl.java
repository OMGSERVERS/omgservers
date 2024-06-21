package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.selectActiveVersionLobbyRequestsByVersionId;

import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionLobbyRequestModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveVersionLobbyRequestsByVersionIdImpl implements SelectActiveVersionLobbyRequestsByVersionId {

    final SelectListOperation selectListOperation;

    final VersionLobbyRequestModelMapper versionLobbyRequestModelMapper;

    @Override
    public Uni<List<VersionLobbyRequestModel>> selectActiveVersionLobbyRequestsByVersionId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long versionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_version_lobby_request
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, versionId),
                "Version lobby request",
                versionLobbyRequestModelMapper::fromRow);
    }
}
