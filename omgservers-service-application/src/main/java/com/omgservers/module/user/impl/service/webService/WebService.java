package com.omgservers.module.user.impl.service.webService;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerObjectRequest;
import com.omgservers.model.dto.user.GetPlayerObjectResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.model.dto.user.UpdatePlayerObjectResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
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
