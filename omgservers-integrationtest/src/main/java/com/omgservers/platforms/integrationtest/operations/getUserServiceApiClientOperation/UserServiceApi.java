package com.omgservers.platforms.integrationtest.operations.getUserServiceApiClientOperation;

import com.omgservers.dto.userModule.CreateTokenRoutedRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeRoutedRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientRoutedRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectRoutedRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerRoutedRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeRoutedRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientRoutedRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectRoutedRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeRoutedRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientRoutedRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectRoutedRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerRoutedRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserRoutedRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsRoutedRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/user-api/v1/request")
public interface UserServiceApi {

    @PUT
    @Path("/sync-user")
    Uni<SyncUserInternalResponse> syncUser(SyncUserRoutedRequest request);

    default SyncUserInternalResponse syncUser(long timeout, SyncUserRoutedRequest request) {
        return syncUser(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/validate-credentials")
    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request);

    @PUT
    @Path("/create-token")
    Uni<CreateTokenInternalResponse> createToken(CreateTokenRoutedRequest request);

    @PUT
    @Path("/introspect-token")
    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    @PUT
    @Path("/get-player")
    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerRoutedRequest request);

    @PUT
    @Path("/sync-player")
    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerRoutedRequest request);

    @PUT
    @Path("/delete-player")
    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerRoutedRequest request);

    default DeletePlayerInternalResponse deletePlayer(long timeout, DeletePlayerRoutedRequest request) {
        return deletePlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-client")
    Uni<SyncClientInternalResponse> syncClient(SyncClientRoutedRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientInternalResponse> getClient(GetClientRoutedRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientRoutedRequest request);

    default DeleteClientInternalResponse deleteClient(long timeout, DeleteClientRoutedRequest request) {
        return deleteClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-attribute")
    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeRoutedRequest request);

    default GetAttributeInternalResponse getAttribute(long timeout, GetAttributeRoutedRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-player-attributes")
    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request);

    default GetPlayerAttributesInternalResponse getPlayerAttributes(long timeout, GetPlayerAttributesRoutedRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-attribute")
    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeRoutedRequest request);

    default SyncAttributeInternalResponse syncAttribute(long timeout, SyncAttributeRoutedRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-attribute")
    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeRoutedRequest request);

    default DeleteAttributeInternalResponse deleteAttribute(long timeout, DeleteAttributeRoutedRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-object")
    Uni<GetObjectInternalResponse> getObject(GetObjectRoutedRequest request);

    default GetObjectInternalResponse getObject(long timeout, GetObjectRoutedRequest request) {
        return getObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-object")
    Uni<SyncObjectInternalResponse> syncObject(SyncObjectRoutedRequest request);

    default SyncObjectInternalResponse syncObject(long timeout, SyncObjectRoutedRequest request) {
        return syncObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-object")
    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectRoutedRequest request);

    default DeleteObjectInternalResponse deleteObject(long timeout, DeleteObjectRoutedRequest request) {
        return deleteObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
