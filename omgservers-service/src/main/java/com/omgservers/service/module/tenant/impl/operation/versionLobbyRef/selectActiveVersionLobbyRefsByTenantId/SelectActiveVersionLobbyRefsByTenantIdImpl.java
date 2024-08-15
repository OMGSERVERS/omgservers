package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectActiveVersionLobbyRefsByTenantId;

import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionLobbyRefModelMapper;
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
class SelectActiveVersionLobbyRefsByTenantIdImpl implements SelectActiveVersionLobbyRefsByTenantId {

    final SelectListOperation selectListOperation;

    final VersionLobbyRefModelMapper versionLobbyRefModelMapper;

    @Override
    public Uni<List<VersionLobbyRefModel>> selectActiveVersionLobbyRefsByTenantId(final SqlConnection sqlConnection,
                                                                                  final int shard,
                                                                                  final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_version_lobby_ref
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Version lobby ref",
                versionLobbyRefModelMapper::fromRow);
    }
}
