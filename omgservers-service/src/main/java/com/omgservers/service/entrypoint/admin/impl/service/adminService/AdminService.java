package com.omgservers.service.entrypoint.admin.impl.service.adminService;

import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface AdminService {

    Uni<CreateTokenAdminResponse> createToken(@Valid CreateTokenAdminRequest request);
}
