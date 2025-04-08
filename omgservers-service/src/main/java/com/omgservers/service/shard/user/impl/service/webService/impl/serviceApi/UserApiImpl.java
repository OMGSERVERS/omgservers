package com.omgservers.service.shard.user.impl.service.webService.impl.serviceApi;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.GetPlayerProfileRequest;
import com.omgservers.schema.module.user.GetPlayerProfileResponse;
import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.schema.module.user.SyncPlayerResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import com.omgservers.service.shard.user.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.SERVICE})
class UserApiImpl implements UserApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetUserResponse> execute(final GetUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncUserResponse> execute(final SyncUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteUserResponse> execute(final DeleteUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateTokenResponse> execute(final CreateTokenRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetPlayerResponse> execute(GetPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindPlayerResponse> execute(FindPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncPlayerResponse> execute(SyncPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdatePlayerProfileResponse> execute(UpdatePlayerProfileRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeletePlayerResponse> execute(DeletePlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetPlayerProfileResponse> execute(GetPlayerProfileRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
