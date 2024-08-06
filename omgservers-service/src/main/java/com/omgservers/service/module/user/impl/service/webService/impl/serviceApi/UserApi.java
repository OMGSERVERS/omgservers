package com.omgservers.service.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.GetPlayerAttributesRequest;
import com.omgservers.schema.module.user.GetPlayerAttributesResponse;
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
import com.omgservers.schema.module.user.UpdatePlayerAttributesRequest;
import com.omgservers.schema.module.user.UpdatePlayerAttributesResponse;
import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "User Module API")
@Path("/omgservers/v1/module/user/request")
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
    @Path("/create-token")
    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);

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
