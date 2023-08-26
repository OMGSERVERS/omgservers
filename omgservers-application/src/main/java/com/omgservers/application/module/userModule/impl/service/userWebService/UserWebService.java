package com.omgservers.application.module.userModule.impl.service.userWebService;

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

public interface UserWebService {

    Uni<SyncUserInternalResponse> syncUser(SyncUserShardRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsShardRequest request);

    Uni<CreateTokenInternalResponse> createToken(CreateTokenShardRequest request);

    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerShardRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerShardRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerShardRequest request);

    Uni<SyncClientInternalResponse> syncClient(SyncClientShardRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientShardRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientShardRequest request);

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request);

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesShardRequest request);

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request);

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeShardRequest request);

    Uni<GetObjectInternalResponse> getObject(GetObjectShardRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectShardRequest request);
}
