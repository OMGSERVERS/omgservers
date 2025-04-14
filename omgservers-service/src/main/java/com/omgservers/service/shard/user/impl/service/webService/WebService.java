package com.omgservers.service.shard.user.impl.service.webService;

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

public interface WebService {

    Uni<GetUserResponse> execute(GetUserRequest request);

    Uni<SyncUserResponse> execute(SyncUserRequest request);

    Uni<DeleteUserResponse> execute(DeleteUserRequest request);

    Uni<CreateTokenResponse> execute(CreateTokenRequest request);

    Uni<GetPlayerResponse> execute(GetPlayerRequest request);

    Uni<GetPlayerProfileResponse> execute(GetPlayerProfileRequest request);

    Uni<FindPlayerResponse> execute(FindPlayerRequest request);

    Uni<SyncPlayerResponse> execute(SyncPlayerRequest request);

    Uni<UpdatePlayerProfileResponse> execute(UpdatePlayerProfileRequest request);

    Uni<DeletePlayerResponse> execute(DeletePlayerRequest request);
}
