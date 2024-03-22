package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createServiceAccount;

import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateServiceAccountMethod {
    Uni<CreateServiceAccountAdminResponse> createServiceAccount(CreateServiceAccountAdminRequest request);
}
