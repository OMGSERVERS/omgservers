package com.omgservers.module.user.impl.service.objectService.impl.method.getObject;

import com.omgservers.dto.user.GetObjectResponse;
import com.omgservers.dto.user.GetObjectRequest;
import io.smallrye.mutiny.Uni;

public interface GetObjectMethod {
    Uni<GetObjectResponse> getObject(GetObjectRequest request);
}
