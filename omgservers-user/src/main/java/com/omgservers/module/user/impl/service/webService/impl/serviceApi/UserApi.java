package com.omgservers.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.DeleteAttributeRequest;
import com.omgservers.dto.user.DeleteAttributeResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeleteObjectRequest;
import com.omgservers.dto.user.DeleteObjectResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.GetAttributeRequest;
import com.omgservers.dto.user.GetAttributeResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetObjectRequest;
import com.omgservers.dto.user.GetObjectResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.dto.user.SyncAttributeRequest;
import com.omgservers.dto.user.SyncAttributeResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncObjectRequest;
import com.omgservers.dto.user.SyncObjectResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/user-api/v1/request")
public interface UserApi {

    @PUT
    @Path("/sync-user")
    Uni<SyncUserResponse> syncUser(SyncUserRequest request);

    default SyncUserResponse syncUser(long timeout, SyncUserRequest request) {
        return syncUser(request).await().atMost(Duration.ofSeconds(timeout));
    }

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
    @Path("/sync-player")
    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);

    @PUT
    @Path("/delete-player")
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);

    default DeletePlayerResponse deletePlayer(long timeout, DeletePlayerRequest request) {
        return deletePlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-client")
    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientResponse> getClient(GetClientRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);

    default DeleteClientResponse deleteClient(long timeout, DeleteClientRequest request) {
        return deleteClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-attribute")
    Uni<GetAttributeResponse> getAttribute(GetAttributeRequest request);

    default GetAttributeResponse getAttribute(long timeout, GetAttributeRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-player-attributes")
    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);

    default GetPlayerAttributesResponse getPlayerAttributes(long timeout, GetPlayerAttributesRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-attribute")
    Uni<SyncAttributeResponse> syncAttribute(SyncAttributeRequest request);

    default SyncAttributeResponse syncAttribute(long timeout, SyncAttributeRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-attribute")
    Uni<DeleteAttributeResponse> deleteAttribute(DeleteAttributeRequest request);

    default DeleteAttributeResponse deleteAttribute(long timeout, DeleteAttributeRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-object")
    Uni<GetObjectResponse> getObject(GetObjectRequest request);

    default GetObjectResponse getObject(long timeout, GetObjectRequest request) {
        return getObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-object")
    Uni<SyncObjectResponse> syncObject(SyncObjectRequest request);

    default SyncObjectResponse syncObject(long timeout, SyncObjectRequest request) {
        return syncObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-object")
    Uni<DeleteObjectResponse> deleteObject(DeleteObjectRequest request);

    default DeleteObjectResponse deleteObject(long timeout, DeleteObjectRequest request) {
        return deleteObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
