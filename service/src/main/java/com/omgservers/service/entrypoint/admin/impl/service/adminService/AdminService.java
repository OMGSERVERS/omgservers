package com.omgservers.service.entrypoint.admin.impl.service.adminService;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface AdminService {

    Uni<CreateTokenAdminResponse> createToken(@Valid CreateTokenAdminRequest request);
}
