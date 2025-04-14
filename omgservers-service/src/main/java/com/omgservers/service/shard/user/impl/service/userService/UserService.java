package com.omgservers.service.shard.user.impl.service.userService;

import com.omgservers.schema.shard.user.CreateTokenRequest;
import com.omgservers.schema.shard.user.CreateTokenResponse;
import com.omgservers.schema.shard.user.DeletePlayerRequest;
import com.omgservers.schema.shard.user.DeletePlayerResponse;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import com.omgservers.schema.shard.user.FindPlayerRequest;
import com.omgservers.schema.shard.user.FindPlayerResponse;
import com.omgservers.schema.shard.user.GetPlayerProfileRequest;
import com.omgservers.schema.shard.user.GetPlayerProfileResponse;
import com.omgservers.schema.shard.user.GetPlayerRequest;
import com.omgservers.schema.shard.user.GetPlayerResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.schema.shard.user.SyncPlayerRequest;
import com.omgservers.schema.shard.user.SyncPlayerResponse;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.schema.shard.user.SyncUserResponse;
import com.omgservers.schema.shard.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.shard.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface UserService {

    Uni<GetUserResponse> execute(@Valid GetUserRequest request);

    Uni<SyncUserResponse> execute(@Valid SyncUserRequest request);

    Uni<SyncUserResponse> executeWithIdempotency(@Valid SyncUserRequest request);

    Uni<DeleteUserResponse> execute(@Valid DeleteUserRequest request);

    Uni<CreateTokenResponse> execute(@Valid CreateTokenRequest request);

    Uni<GetPlayerResponse> execute(@Valid GetPlayerRequest request);

    Uni<GetPlayerProfileResponse> execute(@Valid GetPlayerProfileRequest request);

    Uni<FindPlayerResponse> execute(@Valid FindPlayerRequest request);

    Uni<SyncPlayerResponse> execute(@Valid SyncPlayerRequest request);

    Uni<UpdatePlayerProfileResponse> execute(@Valid UpdatePlayerProfileRequest request);

    Uni<DeletePlayerResponse> execute(@Valid DeletePlayerRequest request);
}
