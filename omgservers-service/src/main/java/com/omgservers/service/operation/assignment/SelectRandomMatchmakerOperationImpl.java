package com.omgservers.service.operation.assignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRandomMatchmakerOperationImpl implements SelectRandomMatchmakerOperation {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    final MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;

    @Override
    public Uni<MatchmakerModel> execute(final Long tenantId,
                                        final Long tenantDeploymentId) {
        return selectTenantMatchmakerRef(tenantId, tenantDeploymentId)
                .flatMap(tenantMatchmakerRef -> {
                    final var matchmakerId = tenantMatchmakerRef.getMatchmakerId();
                    return getMatchmaker(matchmakerId);
                });
    }

    Uni<TenantMatchmakerRefModel> selectTenantMatchmakerRef(final Long tenantId, final Long deploymentId) {
        return viewTenantMatchmakerRefs(tenantId, deploymentId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideNotFoundException(
                                ExceptionQualifierEnum.MATCHMAKER_NOT_FOUND,
                                String.format("matchmaker was not selected, tenantDeployment=%d/%d",
                                        tenantId,
                                        deploymentId));
                    } else {
                        final var randomIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomTenantMatchmakerRef = refs.get(randomIndex);
                        return randomTenantMatchmakerRef;
                    }
                });
    }

    Uni<List<TenantMatchmakerRefModel>> viewTenantMatchmakerRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantMatchmakerRefsRequest(tenantId, deploymentId);
        return tenantShard.getService().viewTenantMatchmakerRefs(request)
                .map(ViewTenantMatchmakerRefsResponse::getTenantMatchmakerRefs);
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }
}
