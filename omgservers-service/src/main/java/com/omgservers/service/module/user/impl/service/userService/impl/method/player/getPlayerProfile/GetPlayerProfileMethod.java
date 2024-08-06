package com.omgservers.service.module.user.impl.service.userService.impl.method.player.getPlayerProfile;

import com.omgservers.schema.module.user.GetPlayerProfileRequest;
import com.omgservers.schema.module.user.GetPlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerProfileMethod {
    Uni<GetPlayerProfileResponse> getPlayerProfile(GetPlayerProfileRequest request);
}
