package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.doMatchmaking;

import com.omgservers.dto.matchmaker.DoMatchmakingRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.tenant.GetStageVersionRequest;
import com.omgservers.dto.tenant.GetStageVersionResponse;
import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
import com.omgservers.module.matchmaker.impl.service.matchmakingService.MatchmakingService;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.version.VersionModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
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

    final MatchmakingService matchmakingService;

    final DoGreedyMatchmakingOperation doMatchmakingOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    @Override
    public Uni<DoMatchmakingShardedResponse> doMatchmaking(DoMatchmakingShardedRequest request) {
        DoMatchmakingShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                final var tenantId = matchmaker.getTenantId();
                                final var stageId = matchmaker.getStageId();
                                final var getStageVersionRequest = new GetStageVersionRequest(tenantId, stageId);
                                return tenantModule.getStageService().getStageVersion(getStageVersionRequest)
                                        .map(GetStageVersionResponse::getVersionId)
                                        .flatMap(versionId -> getVersionStageConfig(versionId)
                                                .flatMap(stageConfig -> doMatchmaking(
                                                        tenantId,
                                                        stageId,
                                                        versionId,
                                                        matchmakerId,
                                                        stageConfig)
                                                        .map(DoMatchmakingShardedResponse::new)));
                            });
                });
    }

    Uni<MatchmakerModel> getMatchmaker(Long id) {
        final var request = new GetMatchmakerShardedRequest(id);
        return matchmakerModule.getMatchmakerShardedService().getMatchmaker(request)
                .map(GetMatchmakerShardedResponse::getMatchmaker);
    }

    Uni<VersionStageConfigModel> getVersionStageConfig(final Long versionId) {
        final var request = new GetStageConfigShardedRequest(versionId);
        return versionModule.getVersionShardedService().getStageConfig(request)
                .map(GetStageConfigShardedResponse::getStageConfig);
    }

    Uni<Boolean> doMatchmaking(final Long tenantId,
                               final Long stageId,
                               final Long versionId,
                               final Long matchmakerId,
                               final VersionStageConfigModel stageConfig) {
        final var requests = matchmakerInMemoryCache.getRequests(matchmakerId);
        final var matches = matchmakerInMemoryCache.getMatches(matchmakerId);
        if (requests.isEmpty()) {
            log.info("There aren't any requests for matchmaking, matchmakerId={}", matchmakerId);
            return Uni.createFrom().item(false);
        } else {
            final var request = DoMatchmakingRequest.builder()
                    .tenantId(tenantId)
                    .stageId(stageId)
                    .versionId(versionId)
                    .matchmakerId(matchmakerId)
                    .requests(requests)
                    .matches(matches)
                    .stageConfig(stageConfig)
                    .build();
            // TODO: detect type of matchmaking
            return matchmakingService.doGreedyMatchmaking(request)
                    .flatMap(this::syncMatchmakingResponse)
                    .replaceWith(true);
        }
    }

    Uni<Void> syncMatchmakingResponse(final DoMatchmakingResponse response) {
        return Uni.createFrom().voidItem();
    }
}
