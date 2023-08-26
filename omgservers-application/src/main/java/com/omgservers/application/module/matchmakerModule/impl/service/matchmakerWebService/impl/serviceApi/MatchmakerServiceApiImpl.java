package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
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
import com.omgservers.model.internalRole.InternalRoleEnum;
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
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchInternalResponse> getMatch(GetMatchShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::doMatchmaking);
    }
}
