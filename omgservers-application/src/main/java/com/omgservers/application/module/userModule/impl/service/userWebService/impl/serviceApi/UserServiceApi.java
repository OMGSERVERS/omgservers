package com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.*;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.GetObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.GetObjectInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.GetPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.GetPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.IntrospectTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.CreateTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.IntrospectTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.CreateUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.SyncUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.ValidateCredentialsInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.SyncUserInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/user-api/v1/request")
public interface UserServiceApi {

    @PUT
    @Path("/create-user")
    Uni<Void> createUser(CreateUserInternalRequest request);

    default void createUser(long timeout, CreateUserInternalRequest request) {
        createUser(request).await().atMost(Duration.ofSeconds(timeout));
    }

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
    Uni<Void> deletePlayer(DeletePlayerInternalRequest request);

    default void deletePlayer(long timeout, DeletePlayerInternalRequest request) {
        deletePlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-client")
    Uni<CreateClientInternalResponse> createClient(CreateClientInternalRequest request);

    @PUT
    @Path("/get-client")
    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    @PUT
    @Path("/delete-client")
    Uni<Void> deleteClient(DeleteClientInternalRequest request);

    default void deleteClient(long timeout, DeleteClientInternalRequest request) {
        deleteClient(request)
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
    Uni<Void> syncObject(SyncObjectInternalRequest request);

    default void syncObject(long timeout, SyncObjectInternalRequest request) {
        syncObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-object")
    Uni<Void> deleteObject(DeleteObjectInternalRequest request);

    default void deleteObject(long timeout, DeleteObjectInternalRequest request) {
        deleteObject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
