package com.omgservers.module.user.impl.service.attributeService.impl.method.syncAttribute;

import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import io.smallrye.mutiny.Uni;

public interface SyncAttributeMethod {
    Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request);
}
