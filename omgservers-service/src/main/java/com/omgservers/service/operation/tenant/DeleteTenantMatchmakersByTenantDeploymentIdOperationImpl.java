package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantMatchmakersByTenantDeploymentIdOperationImpl
        implements DeleteTenantMatchmakersByTenantDeploymentIdOperation {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantMatchmakerRefs(tenantId, tenantDeploymentId)
                .flatMap(tenantMatchmakerRefs -> Multi.createFrom().iterable(tenantMatchmakerRefs)
                        .onItem().transformToUniAndConcatenate(tenantMatchmakerRef ->
                                deleteMatchmaker(tenantMatchmakerRef.getMatchmakerId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete matchmaker, " +
                                                            "tenantDeployment={}/{}, " +
                                                            "matchmakerId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantDeploymentId,
                                                    tenantMatchmakerRef.getMatchmakerId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        }))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantMatchmakerRefModel>> viewTenantMatchmakerRefs(final Long tenantId, final Long tenantDeploymentId) {
        final var request = new ViewTenantMatchmakerRefsRequest(tenantId, tenantDeploymentId);
        return tenantShard.getService().viewTenantMatchmakerRefs(request)
                .map(ViewTenantMatchmakerRefsResponse::getTenantMatchmakerRefs);
    }

    Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }

}
