package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createToken;

import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenAdminResponse> createToken(CreateTokenAdminRequest request);
}
