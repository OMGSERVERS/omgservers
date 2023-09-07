package com.omgservers.module.user.impl.service.objectService.impl.method.deleteObject;

import com.omgservers.dto.user.DeleteObjectResponse;
import com.omgservers.dto.user.DeleteObjectRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteObjectMethod {
    Uni<DeleteObjectResponse> deleteObject(DeleteObjectRequest request);
}
