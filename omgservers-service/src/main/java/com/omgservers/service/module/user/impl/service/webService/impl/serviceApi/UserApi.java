package com.omgservers.service.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerProfileRequest;
import com.omgservers.model.dto.user.GetPlayerProfileResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "User Module API")
@Path("/omgservers/v1/module/user/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.SERVICE_SECURITY_SCHEMA)
public interface UserApi {

    @PUT
    @Path("/get-user")
    Uni<GetUserResponse> getUser(GetUserRequest request);

    @PUT
    @Path("/sync-user")
    Uni<SyncUserResponse> syncUser(SyncUserRequest request);

    @PUT
    @Path("/delete-user")
    Uni<DeleteUserResponse> deleteUser(DeleteUserRequest request);

    @PUT
    @Path("/validate-credentials")
    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);

    @PUT
    @Path("/create-token")
    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);

    @PUT
    @Path("/introspect-token")
    Uni<IntrospectTokenResponse> introspectToken(IntrospectTokenRequest request);

    @PUT
    @Path("/get-player")
    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);

    @PUT
    @Path("/get-player-attributes")
    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);

    @PUT
    @Path("/get-player-profile")
    Uni<GetPlayerProfileResponse> getPlayerProfile(GetPlayerProfileRequest request);

    @PUT
    @Path("/find-player")
    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);

    @PUT
    @Path("/sync-player")
    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);

    @PUT
    @Path("/update-player-attributes")
    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request);

    @PUT
    @Path("/update-player-profile")
    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(@Valid final UpdatePlayerProfileRequest request);

    @PUT
    @Path("/delete-player")
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}
