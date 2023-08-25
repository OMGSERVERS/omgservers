package com.omgservers.application.module.userModule.impl.service.userWebService;

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

public interface UserWebService {

    Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request);

    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);

    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request);

    Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request);

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request);

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request);

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request);

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request);

    Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request);
}
