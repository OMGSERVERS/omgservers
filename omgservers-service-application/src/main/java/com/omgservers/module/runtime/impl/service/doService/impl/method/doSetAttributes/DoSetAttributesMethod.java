package com.omgservers.module.runtime.impl.service.doService.impl.method.doSetAttributes;

import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import io.smallrye.mutiny.Uni;

public interface DoSetAttributesMethod {
    Uni<DoSetAttributesResponse> doSetAttributes(DoSetAttributesRequest request);
}
