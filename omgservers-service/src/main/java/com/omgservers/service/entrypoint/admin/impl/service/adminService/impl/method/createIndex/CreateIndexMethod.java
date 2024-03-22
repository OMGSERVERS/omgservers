package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createIndex;

import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateIndexMethod {
    Uni<CreateIndexAdminResponse> createIndex(CreateIndexAdminRequest request);
}
