package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.GenerateIdAdminRequest;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminResponse;
import io.smallrye.mutiny.Uni;

public interface GenerateIdMethod {
    Uni<GenerateIdAdminResponse> execute(GenerateIdAdminRequest request);
}
