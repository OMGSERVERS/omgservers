package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.findVersionImageRef;

import com.omgservers.model.dto.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.FindVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionImageRefMethod {
    Uni<FindVersionImageRefResponse> findVersionImageRef(FindVersionImageRefRequest request);
}
