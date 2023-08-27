package com.omgservers.module.admin.impl.service.adminService.impl.method.createNewDeveloper;

import com.omgservers.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.dto.admin.CreateDeveloperAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperMethod {
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);
}
