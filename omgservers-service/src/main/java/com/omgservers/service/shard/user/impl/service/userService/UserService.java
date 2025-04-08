package com.omgservers.service.shard.user.impl.service.userService;

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
