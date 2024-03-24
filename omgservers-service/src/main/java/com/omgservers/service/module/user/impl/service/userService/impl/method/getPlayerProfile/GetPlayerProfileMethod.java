package com.omgservers.service.module.user.impl.service.userService.impl.method.getPlayerProfile;

import com.omgservers.model.dto.user.GetPlayerProfileRequest;
import com.omgservers.model.dto.user.GetPlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerProfileMethod {
    Uni<GetPlayerProfileResponse> getPlayerProfile(GetPlayerProfileRequest request);
}
