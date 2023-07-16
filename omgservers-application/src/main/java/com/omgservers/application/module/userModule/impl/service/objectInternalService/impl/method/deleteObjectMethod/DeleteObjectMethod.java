package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.deleteObjectMethod;

import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteObjectMethod {
    Uni<Void> deleteObject(DeleteObjectInternalRequest request);
}
