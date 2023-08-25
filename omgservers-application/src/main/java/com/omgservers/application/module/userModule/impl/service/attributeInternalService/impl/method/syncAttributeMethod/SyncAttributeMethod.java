package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod;

import com.omgservers.dto.userModule.SyncAttributeInternalRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncAttributeMethod {
    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request);
}
