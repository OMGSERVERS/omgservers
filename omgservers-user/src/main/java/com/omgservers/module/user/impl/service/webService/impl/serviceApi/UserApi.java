package com.omgservers.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/user-api/v1/request")
public interface UserApi {

    @PUT
    @Path("/sync-user")
    Uni<SyncUserResponse> syncUser(SyncUserRequest request);

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
    @Path("/get-player-object")
    Uni<GetPlayerObjectResponse> getPlayerObject(GetPlayerObjectRequest request);

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
    @Path("/delete-player")
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);

    @PUT
    @Path("/sync-client")
    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientResponse> getClient(GetClientRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
