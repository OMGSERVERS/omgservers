package com.omgservers.service.module.user.impl.service.userService;

import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.GetPlayerProfileRequest;
import com.omgservers.schema.module.user.GetPlayerProfileResponse;
import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.schema.module.user.SyncPlayerResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface UserService {

    Uni<GetUserResponse> getUser(@Valid GetUserRequest request);

    Uni<SyncUserResponse> syncUser(@Valid SyncUserRequest request);

    Uni<SyncUserResponse> syncUserWithIdempotency(@Valid SyncUserRequest request);

    Uni<DeleteUserResponse> deleteUser(@Valid DeleteUserRequest request);

    Uni<CreateTokenResponse> createToken(@Valid CreateTokenRequest request);

    Uni<GetPlayerResponse> getPlayer(@Valid GetPlayerRequest request);

    Uni<GetPlayerProfileResponse> getPlayerProfile(@Valid GetPlayerProfileRequest request);

    Uni<FindPlayerResponse> findPlayer(@Valid FindPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(@Valid SyncPlayerRequest request);

    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(@Valid UpdatePlayerProfileRequest request);

    Uni<DeletePlayerResponse> deletePlayer(@Valid DeletePlayerRequest request);
}
