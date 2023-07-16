package com.omgservers.application.module.userModule.impl.service.userWebService;

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

public interface UserWebService {

    Uni<Void> createUser(CreateUserInternalRequest request);

    Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request);

    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);

    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);

    Uni<Void> deletePlayer(DeletePlayerInternalRequest request);

    Uni<CreateClientInternalResponse> createClient(CreateClientInternalRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    Uni<Void> deleteClient(DeleteClientInternalRequest request);

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request);

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request);

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request);

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request);

    Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request);

    Uni<Void> syncObject(SyncObjectInternalRequest request);

    Uni<Void> deleteObject(DeleteObjectInternalRequest request);
}
