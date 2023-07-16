package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.getObjectMethod;

import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.GetObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.GetObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetObjectMethod {
    Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request);
}
