package com.omgservers.module.user.impl.service.userWebService.impl.serviceApi;

import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import com.omgservers.dto.user.DeleteAttributeShardResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import com.omgservers.dto.user.DeleteClientShardResponse;
import com.omgservers.dto.user.DeleteObjectShardedResponse;
import com.omgservers.dto.user.DeleteObjectShardedRequest;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.dto.user.DeletePlayerShardResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.IntrospectTokenShardRequest;
import com.omgservers.dto.user.IntrospectTokenShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/user-api/v1/request")
public interface UserServiceApi {

    @PUT
    @Path("/sync-user")
    Uni<SyncUserShardedResponse> syncUser(SyncUserShardedRequest request);

    default SyncUserShardedResponse syncUser(long timeout, SyncUserShardedRequest request) {
        return syncUser(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/validate-credentials")
    Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request);

    @PUT
    @Path("/create-token")
    Uni<CreateTokenShardedResponse> createToken(CreateTokenShardedRequest request);

    @PUT
    @Path("/introspect-token")
    Uni<IntrospectTokenShardedResponse> introspectToken(IntrospectTokenShardRequest request);

    @PUT
    @Path("/get-player")
    Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request);

    @PUT
    @Path("/sync-player")
    Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request);

    @PUT
    @Path("/delete-player")
    Uni<DeletePlayerShardResponse> deletePlayer(DeletePlayerShardedRequest request);

    default DeletePlayerShardResponse deletePlayer(long timeout, DeletePlayerShardedRequest request) {
        return deletePlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-client")
    Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientShardResponse> deleteClient(DeleteClientShardedRequest request);

    default DeleteClientShardResponse deleteClient(long timeout, DeleteClientShardedRequest request) {
        return deleteClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-attribute")
    Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request);

    default GetAttributeShardedResponse getAttribute(long timeout, GetAttributeShardedRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-player-attributes")
    Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request);

    default GetPlayerAttributesShardedResponse getPlayerAttributes(long timeout, GetPlayerAttributesShardedRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-attribute")
    Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request);

    default SyncAttributeShardedResponse syncAttribute(long timeout, SyncAttributeShardedRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-attribute")
    Uni<DeleteAttributeShardResponse> deleteAttribute(DeleteAttributeShardedRequest request);

    default DeleteAttributeShardResponse deleteAttribute(long timeout, DeleteAttributeShardedRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-object")
    Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request);

    default GetObjectShardedResponse getObject(long timeout, GetObjectShardedRequest request) {
        return getObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-object")
    Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request);

    default SyncObjectShardedResponse syncObject(long timeout, SyncObjectShardedRequest request) {
        return syncObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-object")
    Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request);

    default DeleteObjectShardedResponse deleteObject(long timeout, DeleteObjectShardedRequest request) {
        return deleteObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
