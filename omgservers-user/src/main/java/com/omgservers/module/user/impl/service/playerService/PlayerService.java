package com.omgservers.module.user.impl.service.playerService;

import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.dto.user.UpdatePlayerAttributesResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PlayerService {

    Uni<GetPlayerResponse> getPlayer(@Valid GetPlayerRequest request);

    Uni<GetPlayerAttributesResponse> getPlayerAttributes(@Valid GetPlayerAttributesRequest request);

    Uni<GetPlayerObjectResponse> getPlayerObject(@Valid GetPlayerObjectRequest request);

    Uni<FindPlayerResponse> findPlayer(@Valid FindPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(@Valid SyncPlayerRequest request);

    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(@Valid UpdatePlayerAttributesRequest request);

    Uni<DeletePlayerResponse> deletePlayer(@Valid DeletePlayerRequest request);
}
