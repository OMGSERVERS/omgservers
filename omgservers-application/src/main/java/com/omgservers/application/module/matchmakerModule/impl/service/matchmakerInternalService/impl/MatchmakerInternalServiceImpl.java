package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl;

import com.omgservers.application.module.matchmakerModule.impl.operation.getMatchmakerServiceApiClientOperation.GetMatchmakerServiceApiClientOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod.DeleteMatchMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod.DeleteMatchmakerMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod.DeleteRequestMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.doMatchmakingMethod.DoMatchmakingMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchMethod.GetMatchMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchmakerMethod.GetMatchmakerMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchMethod.SyncMatchMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod.SyncMatchmakerMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod.SyncRequestMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi.MatchmakerServiceApi;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.matchmakerModule.DeleteMatchShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingShardRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestShardRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerInternalServiceImpl implements MatchmakerInternalService {

    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchmakerMethod getMatchmakerMethod;
    final SyncRequestMethod syncRequestMethod;
    final DeleteRequestMethod deleteRequestMethod;
    final DoMatchmakingMethod doMatchmakingMethod;
    final DeleteMatchMethod deleteMatchMethod;
    final SyncMatchMethod syncMatchMethod;
    final GetMatchMethod getMatchMethod;

    final GetMatchmakerServiceApiClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncMatchmakerShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::syncMatchmaker,
                syncMatchmakerMethod::syncMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetMatchmakerShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::getMatchmaker,
                getMatchmakerMethod::getMatchmaker);
    }

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteMatchmakerShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::deleteMatchmaker,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRequestShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::syncRequest,
                syncRequestMethod::syncRequest);
    }

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRequestShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::deleteRequest,
                deleteRequestMethod::deleteRequest);
    }

    @Override
    public Uni<GetMatchInternalResponse> getMatch(GetMatchShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetMatchShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::getMatch,
                getMatchMethod::getMatch);
    }

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncMatchShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::syncMatch,
                syncMatchMethod::syncMatch);
    }

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteMatchShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::deleteMatch,
                deleteMatchMethod::deleteMatch);
    }

    @Override
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DoMatchmakingShardRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::doMatchmaking,
                doMatchmakingMethod::doMatchmaking);
    }
}
