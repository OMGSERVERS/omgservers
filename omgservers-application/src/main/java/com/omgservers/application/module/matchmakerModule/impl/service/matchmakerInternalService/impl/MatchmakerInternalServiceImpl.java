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
import com.omgservers.base.impl.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalRequest;
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
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncMatchmakerInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::syncMatchmaker,
                syncMatchmakerMethod::syncMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetMatchmakerInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::getMatchmaker,
                getMatchmakerMethod::getMatchmaker);
    }

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteMatchmakerInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::deleteMatchmaker,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRequestInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::syncRequest,
                syncRequestMethod::syncRequest);
    }

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRequestInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::deleteRequest,
                deleteRequestMethod::deleteRequest);
    }

    @Override
    public Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetMatchInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::getMatch,
                getMatchMethod::getMatch);
    }

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncMatchInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::syncMatch,
                syncMatchMethod::syncMatch);
    }

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteMatchInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::deleteMatch,
                deleteMatchMethod::deleteMatch);
    }

    @Override
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DoMatchmakingInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::doMatchmaking,
                doMatchmakingMethod::doMatchmaking);
    }
}
