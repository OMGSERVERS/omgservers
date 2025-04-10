package com.omgservers.service.shard.user.impl.service.webService;

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
