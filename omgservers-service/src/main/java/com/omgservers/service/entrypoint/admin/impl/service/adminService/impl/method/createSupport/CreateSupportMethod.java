package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createSupport;

import com.omgservers.model.dto.admin.CreateSupportAdminRequest;
import com.omgservers.model.dto.admin.CreateSupportAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateSupportMethod {
    Uni<CreateSupportAdminResponse> createSupport(CreateSupportAdminRequest request);
}
