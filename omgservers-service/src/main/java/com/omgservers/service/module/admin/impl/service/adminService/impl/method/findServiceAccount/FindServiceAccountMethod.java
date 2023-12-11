package com.omgservers.service.module.admin.impl.service.adminService.impl.method.findServiceAccount;

import com.omgservers.model.dto.admin.FindServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.FindServiceAccountAdminResponse;
import io.smallrye.mutiny.Uni;

public interface FindServiceAccountMethod {
    Uni<FindServiceAccountAdminResponse> findServiceAccount(FindServiceAccountAdminRequest request);
}
