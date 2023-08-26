package com.omgservers.platforms.integrationtest.operations.getUserServiceApiClientOperation;

import com.omgservers.dto.userModule.CreateTokenShardRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeShardRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientShardRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectShardRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerShardRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeShardRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientShardRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectShardRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesShardRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerShardRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeShardRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientShardRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectShardRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerShardRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserShardRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsShardRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/user-api/v1/request")
public interface UserServiceApi {

    @PUT
    @Path("/sync-user")
    Uni<SyncUserInternalResponse> syncUser(SyncUserShardRequest request);

    default SyncUserInternalResponse syncUser(long timeout, SyncUserShardRequest request) {
        return syncUser(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/validate-credentials")
    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsShardRequest request);

    @PUT
    @Path("/create-token")
    Uni<CreateTokenInternalResponse> createToken(CreateTokenShardRequest request);

    @PUT
    @Path("/introspect-token")
    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    @PUT
    @Path("/get-player")
    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerShardRequest request);

    @PUT
    @Path("/sync-player")
    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerShardRequest request);

    @PUT
    @Path("/delete-player")
    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerShardRequest request);

    default DeletePlayerInternalResponse deletePlayer(long timeout, DeletePlayerShardRequest request) {
        return deletePlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-client")
    Uni<SyncClientInternalResponse> syncClient(SyncClientShardRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientInternalResponse> getClient(GetClientShardRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientShardRequest request);

    default DeleteClientInternalResponse deleteClient(long timeout, DeleteClientShardRequest request) {
        return deleteClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-attribute")
    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request);

    default GetAttributeInternalResponse getAttribute(long timeout, GetAttributeShardRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-player-attributes")
    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesShardRequest request);

    default GetPlayerAttributesInternalResponse getPlayerAttributes(long timeout, GetPlayerAttributesShardRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-attribute")
    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request);

    default SyncAttributeInternalResponse syncAttribute(long timeout, SyncAttributeShardRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-attribute")
    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeShardRequest request);

    default DeleteAttributeInternalResponse deleteAttribute(long timeout, DeleteAttributeShardRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-object")
    Uni<GetObjectInternalResponse> getObject(GetObjectShardRequest request);

    default GetObjectInternalResponse getObject(long timeout, GetObjectShardRequest request) {
        return getObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-object")
    Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request);

    default SyncObjectInternalResponse syncObject(long timeout, SyncObjectShardRequest request) {
        return syncObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-object")
    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectShardRequest request);

    default DeleteObjectInternalResponse deleteObject(long timeout, DeleteObjectShardRequest request) {
        return deleteObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
