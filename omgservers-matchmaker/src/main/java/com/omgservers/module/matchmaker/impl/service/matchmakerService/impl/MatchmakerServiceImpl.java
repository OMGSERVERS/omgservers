package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerResponse;
import com.omgservers.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.dto.matchmaker.SyncMatchRequest;
import com.omgservers.dto.matchmaker.SyncMatchResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.module.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatch.DeleteMatchMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchClient.DeleteMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmaker.DeleteMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteRequest.DeleteRequestMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.executeMatchmaker.ExecuteMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatch.GetMatchMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchClient.GetMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmaker.GetMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatch.SyncMatchMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchClient.SyncMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmaker.SyncMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncRequest.SyncRequestMethod;
import com.omgservers.module.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerServiceImpl implements MatchmakerService {

    final DeleteMatchClientMethod deleteMatchClientMethod;
    final ExecuteMatchmakerMethod executeMatchmakerMethod;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final SyncMatchClientMethod syncMatchClientMethod;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchClientMethod getMatchClientMethod;
    final GetMatchmakerMethod getMatchmakerMethod;
    final DeleteRequestMethod deleteRequestMethod;
    final SyncRequestMethod syncRequestMethod;
    final DeleteMatchMethod deleteMatchMethod;
    final SyncMatchMethod syncMatchMethod;
    final GetMatchMethod getMatchMethod;

    final GetMatchmakerModuleClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(@Valid final SyncMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmaker,
                syncMatchmakerMethod::syncMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(@Valid final GetMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmaker,
                getMatchmakerMethod::getMatchmaker);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(@Valid final DeleteMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmaker,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    @Override
    public Uni<SyncRequestResponse> syncRequest(@Valid final SyncRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncRequest,
                syncRequestMethod::syncRequest);
    }

    @Override
    public Uni<DeleteRequestResponse> deleteRequest(@Valid final DeleteRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteRequest,
                deleteRequestMethod::deleteRequest);
    }

    @Override
    public Uni<GetMatchResponse> getMatch(@Valid final GetMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatch,
                getMatchMethod::getMatch);
    }

    @Override
    public Uni<SyncMatchResponse> syncMatch(@Valid final SyncMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatch,
                syncMatchMethod::syncMatch);
    }

    @Override
    public Uni<DeleteMatchResponse> deleteMatch(@Valid final DeleteMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatch,
                deleteMatchMethod::deleteMatch);
    }

    @Override
    public Uni<GetMatchClientResponse> getMatchClient(@Valid final GetMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchClient,
                getMatchClientMethod::getMatchClient);
    }

    @Override
    public Uni<SyncMatchClientResponse> syncMatchClient(@Valid final SyncMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchClient,
                syncMatchClientMethod::syncMatchClient);
    }

    @Override
    public Uni<DeleteMatchClientResponse> deleteMatchClient(@Valid final DeleteMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchClient,
                deleteMatchClientMethod::deleteMatchClient);
    }

    @Override
    public Uni<ExecuteMatchmakerResponse> executeMatchmaker(@Valid final ExecuteMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::executeMatchmaker,
                executeMatchmakerMethod::executeMatchmaker);
    }
}
