package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncAttributeMethod {
    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request);
}
