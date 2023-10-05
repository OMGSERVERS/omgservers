package com.omgservers.module.user.impl.service.webService;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.dto.user.UpdatePlayerObjectResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<SyncUserResponse> syncUser(SyncUserRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);

    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);

    Uni<IntrospectTokenResponse> introspectToken(IntrospectTokenRequest request);

    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);

    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);

    Uni<GetPlayerObjectResponse> getPlayerObject(GetPlayerObjectRequest request);

    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);

    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request);

    Uni<UpdatePlayerObjectResponse> updatePlayerObject(UpdatePlayerObjectRequest request);

    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);

    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    Uni<GetClientResponse> getClient(GetClientRequest request);

    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
