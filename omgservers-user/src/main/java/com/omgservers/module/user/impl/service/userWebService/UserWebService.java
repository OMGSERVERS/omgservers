package com.omgservers.module.user.impl.service.userWebService;

import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.DeleteAttributeShardedResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import com.omgservers.dto.user.DeleteClientShardedResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import com.omgservers.dto.user.DeleteObjectShardedResponse;
import com.omgservers.dto.user.DeleteObjectShardedRequest;
import com.omgservers.dto.user.DeletePlayerShardedResponse;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.dto.user.IntrospectTokenShardedRequest;
import com.omgservers.dto.user.IntrospectTokenShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
import io.smallrye.mutiny.Uni;

public interface UserWebService {

    Uni<SyncUserShardedResponse> syncUser(SyncUserShardedRequest request);

    Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request);

    Uni<CreateTokenShardedResponse> createToken(CreateTokenShardedRequest request);

    Uni<IntrospectTokenShardedResponse> introspectToken(IntrospectTokenShardedRequest request);

    Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request);

    Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request);

    Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request);

    Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request);

    Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request);

    Uni<DeleteClientShardedResponse> deleteClient(DeleteClientShardedRequest request);

    Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request);

    Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request);

    Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request);

    Uni<DeleteAttributeShardedResponse> deleteAttribute(DeleteAttributeShardedRequest request);

    Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request);

    Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request);

    Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request);
}
