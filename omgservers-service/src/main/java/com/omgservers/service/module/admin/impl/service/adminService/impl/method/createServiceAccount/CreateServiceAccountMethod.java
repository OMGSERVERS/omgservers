package com.omgservers.service.module.admin.impl.service.adminService.impl.method.createServiceAccount;

import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateServiceAccountMethod {
    Uni<CreateServiceAccountAdminResponse> createServiceAccount(CreateServiceAccountAdminRequest request);
}
