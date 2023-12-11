package com.omgservers.service.module.admin.impl.service.adminService.impl.method.findIndex;

import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import io.smallrye.mutiny.Uni;

public interface FindIndexMethod {
    Uni<FindIndexAdminResponse> findIndex(FindIndexAdminRequest request);
}
