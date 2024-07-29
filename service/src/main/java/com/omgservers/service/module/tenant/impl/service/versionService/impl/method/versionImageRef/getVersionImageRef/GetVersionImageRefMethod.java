package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.getVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionImageRefMethod {

    Uni<GetVersionImageRefResponse> getVersionImageRef(GetVersionImageRefRequest request);
}
