package com.omgservers.module.user.impl.service.attributeService.impl.method.syncAttribute;

import com.omgservers.dto.user.SyncAttributeResponse;
import com.omgservers.dto.user.SyncAttributeRequest;
import io.smallrye.mutiny.Uni;

public interface SyncAttributeMethod {
    Uni<SyncAttributeResponse> syncAttribute(SyncAttributeRequest request);
}
