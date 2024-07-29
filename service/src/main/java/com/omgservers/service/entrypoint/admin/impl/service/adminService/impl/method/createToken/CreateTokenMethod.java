package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createToken;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenAdminResponse> createToken(CreateTokenAdminRequest request);
}
