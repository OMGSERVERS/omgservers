package com.omgservers.service.module.tenant.impl.operation.selectVersionsByStageId;

import com.omgservers.model.version.VersionModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionsByStageIdOperationImpl implements SelectVersionsByStageIdOperation {

    final SelectListOperation selectListOperation;

    final VersionModelMapper versionModelMapper;

    @Override
    public Uni<List<VersionModel>> selectVersionsByStageId(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long tenantId,
                                                           final Long stageId,
                                                           final Boolean deleted) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, stage_id, created, modified,
                            default_matchmaker_id, default_runtime_id, config, source_code, deleted
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and stage_id = $2 and deleted = $3
                        """,
                Arrays.asList(
                        tenantId,
                        stageId,
                        deleted
                ),
                "Version",
                versionModelMapper::fromRow);
    }
}
