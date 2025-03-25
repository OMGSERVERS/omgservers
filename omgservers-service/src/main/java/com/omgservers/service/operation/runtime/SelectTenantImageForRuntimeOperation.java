package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import io.smallrye.mutiny.Uni;

public interface SelectTenantImageForRuntimeOperation {
    Uni<TenantImageModel> execute(RuntimeQualifierEnum runtimeQualifier,
                                  Long tenantId,
                                  Long tenantVersionId);
}
