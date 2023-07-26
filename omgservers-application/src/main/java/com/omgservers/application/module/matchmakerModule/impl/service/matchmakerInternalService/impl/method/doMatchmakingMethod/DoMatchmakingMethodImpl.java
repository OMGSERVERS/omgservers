package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.doMatchmakingMethod;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation.DeleteRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation.DoGreedyMatchmakingOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation.UpsertMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DoMatchmakingInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.GetMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DoMatchmakingInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.GetMatchmakerInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.MatchmakingHelpService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request.DoGreedyMatchmakingHelpRequest;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetStageConfigInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
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
    final PgPool pgPool;

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
