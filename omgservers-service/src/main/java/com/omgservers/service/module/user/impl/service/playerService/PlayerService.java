package com.omgservers.service.module.user.impl.service.playerService;

import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerProfileRequest;
import com.omgservers.model.dto.user.GetPlayerProfileResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PlayerService {

    Uni<GetPlayerResponse> getPlayer(@Valid GetPlayerRequest request);

    Uni<GetPlayerAttributesResponse> getPlayerAttributes(@Valid GetPlayerAttributesRequest request);

    Uni<GetPlayerProfileResponse> getPlayerProfile(@Valid GetPlayerProfileRequest request);

    Uni<FindPlayerResponse> findPlayer(@Valid FindPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(@Valid SyncPlayerRequest request);

    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(@Valid UpdatePlayerAttributesRequest request);

    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(@Valid UpdatePlayerProfileRequest request);

    Uni<DeletePlayerResponse> deletePlayer(@Valid DeletePlayerRequest request);
}
