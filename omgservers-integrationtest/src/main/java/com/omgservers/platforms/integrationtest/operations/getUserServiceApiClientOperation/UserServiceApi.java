package com.omgservers.platforms.integrationtest.operations.getUserServiceApiClientOperation;

import com.omgservers.dto.userModule.CreateTokenInternalRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeInternalRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientInternalRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectInternalRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerInternalRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeInternalRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectInternalRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerInternalRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeInternalRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientInternalRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectInternalRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerInternalRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserInternalRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsInternalRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/user-api/v1/request")
public interface UserServiceApi {

    @PUT
    @Path("/sync-user")
    Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request);

    default SyncUserInternalResponse syncUser(long timeout, SyncUserInternalRequest request) {
        return syncUser(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/validate-credentials")
    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request);

    @PUT
    @Path("/create-token")
    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);

    @PUT
    @Path("/introspect-token")
    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    @PUT
    @Path("/get-player")
    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);

    @PUT
    @Path("/sync-player")
    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);

    @PUT
    @Path("/delete-player")
    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request);

    default DeletePlayerInternalResponse deletePlayer(long timeout, DeletePlayerInternalRequest request) {
        return deletePlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-client")
    Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request);

    default DeleteClientInternalResponse deleteClient(long timeout, DeleteClientInternalRequest request) {
        return deleteClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-attribute")
    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request);

    default GetAttributeInternalResponse getAttribute(long timeout, GetAttributeInternalRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-player-attributes")
    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request);

    default GetPlayerAttributesInternalResponse getPlayerAttributes(long timeout, GetPlayerAttributesInternalRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-attribute")
    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request);

    default SyncAttributeInternalResponse syncAttribute(long timeout, SyncAttributeInternalRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-attribute")
    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request);

    default DeleteAttributeInternalResponse deleteAttribute(long timeout, DeleteAttributeInternalRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-object")
    Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request);

    default GetObjectInternalResponse getObject(long timeout, GetObjectInternalRequest request) {
        return getObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-object")
    Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request);

    default SyncObjectInternalResponse syncObject(long timeout, SyncObjectInternalRequest request) {
        return syncObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-object")
    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request);

    default DeleteObjectInternalResponse deleteObject(long timeout, DeleteObjectInternalRequest request) {
        return deleteObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
