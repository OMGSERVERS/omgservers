package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.viewVersionImageRefs;

import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsResponse;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByTenantId.SelectActiveVersionImageRefsByTenantId;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByVersionId.SelectActiveVersionImageRefsByVersionId;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionImageRefsMethodImpl implements ViewVersionImageRefsMethod {

    final SelectActiveVersionImageRefsByVersionId selectActiveVersionImageRefsByVersionId;
    final SelectActiveVersionImageRefsByTenantId selectActiveVersionImageRefsByTenantId;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(final ViewVersionImageRefsRequest request) {
        log.debug("View version image refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                                final var versionId = request.getVersionId();
                                if (Objects.nonNull(versionId)) {
                                    return selectActiveVersionImageRefsByVersionId
                                            .selectActiveVersionImageRefsByVersionId(sqlConnection,
                                                    shard.shard(),
                                                    tenantId,
                                                    versionId);
                                } else {
                                    return selectActiveVersionImageRefsByTenantId
                                            .selectActiveVersionImageRefsByTenantId(sqlConnection,
                                                    shard.shard(),
                                                    tenantId);
                                }
                            }
                    );
                })
                .map(ViewVersionImageRefsResponse::new);
    }
}
