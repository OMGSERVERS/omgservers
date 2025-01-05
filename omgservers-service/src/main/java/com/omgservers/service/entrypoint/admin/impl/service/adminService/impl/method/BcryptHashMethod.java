package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.BcryptHashAdminRequest;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminResponse;
import io.smallrye.mutiny.Uni;

public interface BcryptHashMethod {
    Uni<BcryptHashAdminResponse> execute(BcryptHashAdminRequest request);
}
