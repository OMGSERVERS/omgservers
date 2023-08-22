package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.deleteObjectMethod;

import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.DeleteObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteObjectMethod {
    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request);
}
