package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doSetProfile;

import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import io.smallrye.mutiny.Uni;

public interface DoSetProfileMethod {
    Uni<DoSetProfileResponse> doSetProfile(DoSetProfileRequest request);
}
