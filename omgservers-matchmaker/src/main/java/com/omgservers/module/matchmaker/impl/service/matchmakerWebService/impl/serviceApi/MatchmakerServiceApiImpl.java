package com.omgservers.module.matchmaker.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.dto.matchmaker.DeleteMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardedResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.matchmaker.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerServiceApiImpl implements MatchmakerServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final MatchmakerWebService matchmakerWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerShardedResponse> syncMatchmaker(SyncMatchmakerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerShardedResponse> getMatchmaker(GetMatchmakerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerShardedResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRequestShardedResponse> syncRequest(SyncRequestShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRequestShardedResponse> deleteRequest(DeleteRequestShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchShardedResponse> getMatch(GetMatchShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchShardedResponse> syncMatch(SyncMatchShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchShardedResponse> deleteMatch(DeleteMatchShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchClientShardedResponse> getMatchClient(GetMatchClientShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchClientShardedResponse> syncMatchClient(SyncMatchClientShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchClientShardedResponse> deleteMatchClient(DeleteMatchClientShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ExecuteMatchmakerShardedResponse> executeMatchmaker(ExecuteMatchmakerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::executeMatchmaker);
    }
}
