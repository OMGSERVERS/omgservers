package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl;

import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchmakerOperation.DeleteMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.getMatchmakerServiceApiClientOperation.GetMatchmakerServiceApiClientOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createMatchmakerMethod.CreateMatchmakerMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createRequestMethod.CreateRequestMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod.DeleteMatchMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod.DeleteMatchmakerMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod.DeleteRequestMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.doMatchmakingMethod.DoMatchmakingMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchMethod.GetMatchMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchmakerMethod.GetMatchmakerMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchMethod.SyncMatchMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.*;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.*;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi.MatchmakerServiceApi;
import com.omgservers.application.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerInternalServiceImpl implements MatchmakerInternalService {

    final CreateMatchmakerMethod createMatchmakerMethod;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final GetMatchmakerMethod getMatchmakerMethod;
    final CreateRequestMethod createRequestMethod;
    final DeleteRequestMethod deleteRequestMethod;
    final DoMatchmakingMethod doMatchmakingMethod;
    final DeleteMatchMethod deleteMatchMethod;
    final SyncMatchMethod syncMatchMethod;
    final GetMatchMethod getMatchMethod;

    final GetMatchmakerServiceApiClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<Void> createMatchmaker(CreateMatchmakerInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateMatchmakerInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::createMatchmaker,
                createMatchmakerMethod::createMatchmaker);
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
    public Uni<CreateRequestInternalResponse> createRequest(CreateRequestInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateRequestInternalRequest::validate,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerServiceApi::createRequest,
                createRequestMethod::createRequest);
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
