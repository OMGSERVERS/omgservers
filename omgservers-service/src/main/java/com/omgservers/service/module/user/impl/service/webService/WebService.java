package com.omgservers.service.module.user.impl.service.webService;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerProfileRequest;
import com.omgservers.model.dto.user.GetPlayerProfileResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetUserResponse> getUser(GetUserRequest request);

    Uni<SyncUserResponse> syncUser(SyncUserRequest request);

    Uni<DeleteUserResponse> deleteUser(DeleteUserRequest request);

    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);

    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);

    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);

    Uni<GetPlayerProfileResponse> getPlayerProfile(GetPlayerProfileRequest request);

    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);

    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request);

    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(UpdatePlayerProfileRequest request);

    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}
