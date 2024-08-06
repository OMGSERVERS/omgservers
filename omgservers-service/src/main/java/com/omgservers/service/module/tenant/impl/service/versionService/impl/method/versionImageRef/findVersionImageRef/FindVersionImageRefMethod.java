package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.findVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionImageRefMethod {
    Uni<FindVersionImageRefResponse> findVersionImageRef(FindVersionImageRefRequest request);
}
