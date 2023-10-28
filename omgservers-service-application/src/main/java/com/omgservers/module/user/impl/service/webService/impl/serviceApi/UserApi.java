package com.omgservers.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerObjectRequest;
import com.omgservers.model.dto.user.GetPlayerObjectResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.model.dto.user.UpdatePlayerObjectResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
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
    @Path("/update-player-object")
    Uni<UpdatePlayerObjectResponse> updatePlayerObject(@Valid final UpdatePlayerObjectRequest request);

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
