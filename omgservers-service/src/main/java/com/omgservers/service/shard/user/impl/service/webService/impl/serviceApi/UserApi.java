package com.omgservers.service.shard.user.impl.service.webService.impl.serviceApi;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "User Shard API")
@Path("/service/v1/shard/user/request")
public interface UserApi {

    @POST
    @Path("/get-user")
    Uni<GetUserResponse> execute(GetUserRequest request);

    @POST
    @Path("/sync-user")
    Uni<SyncUserResponse> execute(SyncUserRequest request);

    @POST
    @Path("/delete-user")
    Uni<DeleteUserResponse> execute(DeleteUserRequest request);

    @POST
    @Path("/create-token")
    Uni<CreateTokenResponse> execute(CreateTokenRequest request);

    @POST
    @Path("/get-player")
    Uni<GetPlayerResponse> execute(GetPlayerRequest request);

    @POST
    @Path("/get-player-profile")
    Uni<GetPlayerProfileResponse> execute(GetPlayerProfileRequest request);

    @POST
    @Path("/find-player")
    Uni<FindPlayerResponse> execute(FindPlayerRequest request);

    @POST
    @Path("/sync-player")
    Uni<SyncPlayerResponse> execute(SyncPlayerRequest request);

    @POST
    @Path("/update-player-profile")
    Uni<UpdatePlayerProfileResponse> execute(UpdatePlayerProfileRequest request);

    @POST
    @Path("/delete-player")
    Uni<DeletePlayerResponse> execute(DeletePlayerRequest request);
}
