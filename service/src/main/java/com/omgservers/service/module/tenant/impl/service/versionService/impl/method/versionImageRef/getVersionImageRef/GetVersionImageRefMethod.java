package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.getVersionImageRef;

import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionImageRefMethod {

    Uni<GetVersionImageRefResponse> getVersionImageRef(GetVersionImageRefRequest request);
}
