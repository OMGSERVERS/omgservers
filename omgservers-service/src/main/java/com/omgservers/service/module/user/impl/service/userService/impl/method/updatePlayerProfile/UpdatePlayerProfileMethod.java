package com.omgservers.service.module.user.impl.service.userService.impl.method.updatePlayerProfile;

import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerProfileMethod {
    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(UpdatePlayerProfileRequest request);
}
