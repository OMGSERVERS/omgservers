package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersionData;

import com.omgservers.schema.module.tenant.version.GetVersionDataRequest;
import com.omgservers.schema.module.tenant.version.GetVersionDataResponse;
import com.omgservers.schema.module.tenant.version.dto.VersionDataDto;
import com.omgservers.service.module.tenant.impl.operation.version.selectVersion.SelectVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByVersionId.SelectActiveVersionImageRefsByVersionId;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectActiveVersionLobbyRefsByVersionId.SelectActiveVersionLobbyRefsByVersionId;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectActiveVersionMatchmakerRefsByVersionId.SelectActiveVersionMatchmakerRefsByVersionId;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionDataMethodImpl implements GetVersionDataMethod {

    final SelectActiveVersionMatchmakerRefsByVersionId selectActiveVersionMatchmakerRefsByVersionId;
    final SelectActiveVersionLobbyRefsByVersionId selectActiveVersionLobbyRefsByVersionId;
    final SelectActiveVersionImageRefsByVersionId selectActiveVersionImageRefsByVersionId;
    final SelectVersionOperation selectVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionDataResponse> getVersionData(final GetVersionDataRequest request) {
        log.debug("Get version data, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var versionData = new VersionDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, versionId, versionData));
                })
                .map(GetVersionDataResponse::new);
    }

    Uni<VersionDataDto> fillData(final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long tenantId,
                                 final Long versionId,
                                 final VersionDataDto versionData) {
        return selectVersion(sqlConnection, shard, tenantId, versionId, versionData)
                .flatMap(voidItem -> selectImageRefs(sqlConnection, shard, tenantId, versionId, versionData))
                .flatMap(voidItem -> selectLobbyRefs(sqlConnection, shard, tenantId, versionId, versionData))
                .flatMap(voidItem -> selectMatchmakerRefs(sqlConnection, shard, tenantId, versionId, versionData))
                .replaceWith(versionData);
    }

    Uni<Void> selectVersion(final SqlConnection sqlConnection,
                            final int shard,
                            final Long tenantId,
                            final Long versionId,
                            final VersionDataDto versionData) {
        return selectVersionOperation.selectVersion(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setVersion)
                .replaceWithVoid();
    }

    Uni<Void> selectImageRefs(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long versionId,
                              final VersionDataDto versionData) {
        return selectActiveVersionImageRefsByVersionId.selectActiveVersionImageRefsByVersionId(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setImageRefs)
                .replaceWithVoid();
    }

    Uni<Void> selectLobbyRefs(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long versionId,
                              final VersionDataDto versionData) {
        return selectActiveVersionLobbyRefsByVersionId.selectActiveVersionLobbyRefsByVersionId(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setLobbyRefs)
                .replaceWithVoid();
    }

    Uni<Void> selectMatchmakerRefs(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long tenantId,
                                   final Long versionId,
                                   final VersionDataDto versionData) {
        return selectActiveVersionMatchmakerRefsByVersionId.selectActiveVersionMatchmakerRefsByVersionId(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setMatchmakerRefs)
                .replaceWithVoid();
    }
}
