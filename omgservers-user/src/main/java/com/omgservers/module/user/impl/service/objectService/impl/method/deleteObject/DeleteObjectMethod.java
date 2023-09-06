package com.omgservers.module.user.impl.service.objectService.impl.method.deleteObject;

import com.omgservers.dto.user.DeleteObjectShardedResponse;
import com.omgservers.dto.user.DeleteObjectShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteObjectMethod {
    Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request);
}
