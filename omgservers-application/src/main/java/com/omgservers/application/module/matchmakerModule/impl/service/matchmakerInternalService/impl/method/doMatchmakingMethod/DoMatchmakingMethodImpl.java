package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.doMatchmakingMethod;

import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation.DeleteRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation.DoGreedyMatchmakingOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation.UpsertMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.MatchmakingHelpService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request.DoGreedyMatchmakingHelpRequest;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.base.impl.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigInternalRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionStageConfigModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DoMatchmakingMethodImpl implements DoMatchmakingMethod {

    final MatchmakerModule matchmakerModule;
    final VersionModule versionModule;
    final TenantModule tenantModule;

    final MatchmakingHelpService matchmakingHelpService;

    final DoGreedyMatchmakingOperation doMatchmakingOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    @Override
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingInternalRequest request) {
        DoMatchmakingInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                final var tenant = matchmaker.getTenantId();
                                final var stage = matchmaker.getStageId();
                                return getStageVersion(tenant, stage)
                                        .flatMap(version -> getStageConfig(version)
                                                .flatMap(stageConfig -> doMatchmaking(
                                                        tenant,
                                                        stage,
                                                        version,
                                                        matchmakerId,
                                                        stageConfig)))
                                        .map(DoMatchmakingInternalResponse::new);
                            });
                });
    }

    Uni<MatchmakerModel> getMatchmaker(Long id) {
        final var request = new GetMatchmakerInternalRequest(id);
        return matchmakerModule.getMatchmakerInternalService().getMatchmaker(request)
                .map(GetMatchmakerInternalResponse::getMatchmaker);
    }

    Uni<Long> getStageVersion(final Long tenantId, final Long stageId) {
        final var request = new GetStageInternalRequest(tenantId, stageId);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage)
                .map(StageModel::getVersionId)
                .onItem().ifNull().failWith(new ServerSideConflictException(String
                        .format("no any stage's version wasn't deployed yet, tenantId=%d, stageId=%d", tenantId, stageId)));
    }

    Uni<VersionStageConfigModel> getStageConfig(final Long versionId) {
        final var request = new GetStageConfigInternalRequest(versionId);
        return versionModule.getVersionInternalService().getStageConfig(request)
                .map(GetStageConfigInternalResponse::getStageConfig);
    }

    Uni<Boolean> doMatchmaking(final Long tenantId,
                               final Long stageId,
                               final Long versionId,
                               final Long matchmakerId,
                               final VersionStageConfigModel stageConfig) {
        final var requests = matchmakerInMemoryCache.getRequests(matchmakerId);
        final var matches = matchmakerInMemoryCache.getMatches(matchmakerId);
        if (requests.isEmpty()) {
            log.info("There aren't any requests to matchmaking, matchmakerId={}", matchmakerId);
            return Uni.createFrom().item(false);
        } else {
            final var request = new DoGreedyMatchmakingHelpRequest(
                    tenantId,
                    stageId,
                    versionId,
                    matchmakerId,
                    requests,
                    matches,
                    stageConfig);
            return matchmakingHelpService.doGreedyMatchmaking(request)
                    .replaceWith(true);
        }
    }
}
