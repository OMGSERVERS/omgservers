package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.deleteTenant;

import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.admin.DeleteTenantAdminResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<DeleteTenantAdminResponse> deleteTenant(DeleteTenantAdminRequest request);

}
