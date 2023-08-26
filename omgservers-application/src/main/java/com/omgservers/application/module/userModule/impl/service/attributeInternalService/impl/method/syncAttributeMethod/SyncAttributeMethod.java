package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod;

import com.omgservers.dto.userModule.SyncAttributeShardRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncAttributeMethod {
    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request);
}
