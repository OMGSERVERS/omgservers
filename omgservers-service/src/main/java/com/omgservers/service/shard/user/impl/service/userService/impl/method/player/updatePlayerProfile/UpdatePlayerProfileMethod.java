package com.omgservers.service.shard.user.impl.service.userService.impl.method.player.updatePlayerProfile;

import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerProfileMethod {
    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(UpdatePlayerProfileRequest request);
}
