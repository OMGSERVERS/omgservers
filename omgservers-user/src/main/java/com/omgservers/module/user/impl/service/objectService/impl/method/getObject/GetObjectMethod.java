package com.omgservers.module.user.impl.service.objectService.impl.method.getObject;

import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import io.smallrye.mutiny.Uni;

public interface GetObjectMethod {
    Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request);
}
