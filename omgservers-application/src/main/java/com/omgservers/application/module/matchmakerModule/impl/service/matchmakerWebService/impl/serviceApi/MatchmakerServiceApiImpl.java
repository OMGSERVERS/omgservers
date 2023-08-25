package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.base.impl.operation.handleApiRequestOperation.HandleApiRequestOperation;
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
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::doMatchmaking);
    }
}
