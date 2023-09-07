package com.omgservers.module.matchmaker.impl.service.webService.impl.api;

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
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.matchmaker.impl.service.webService.WebService;
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
public class MatchmakerApiImpl implements MatchmakerApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRequestResponse> syncRequest(SyncRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchResponse> getMatch(GetMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchResponse> syncMatch(SyncMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchResponse> deleteMatch(DeleteMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchClientResponse> getMatchClient(GetMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchClientResponse> syncMatchClient(SyncMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchClientResponse> deleteMatchClient(DeleteMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ExecuteMatchmakerResponse> executeMatchmaker(ExecuteMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::executeMatchmaker);
    }
}
