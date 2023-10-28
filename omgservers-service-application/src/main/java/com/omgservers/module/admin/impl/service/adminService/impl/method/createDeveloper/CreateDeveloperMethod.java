package com.omgservers.module.admin.impl.service.adminService.impl.method.createDeveloper;

import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperMethod {
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);
}
