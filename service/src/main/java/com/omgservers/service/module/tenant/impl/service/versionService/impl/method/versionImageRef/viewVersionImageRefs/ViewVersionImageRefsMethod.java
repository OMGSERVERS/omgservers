package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.viewVersionImageRefs;

import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionImageRefsMethod {
    Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(ViewVersionImageRefsRequest request);
}
