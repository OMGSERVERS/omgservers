package com.omgservers.service.shard.user.impl.service.webService.impl.serviceApi;

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
