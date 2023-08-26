package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.base.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.matchmakerModule.DeleteMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingRoutedRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestRoutedRequest;
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
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchInternalResponse> getMatch(GetMatchRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::getMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::syncMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::deleteMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, matchmakerWebService::doMatchmaking);
    }
}
