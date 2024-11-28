package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CalculateShardMethod {
    Uni<CalculateShardAdminResponse> execute(CalculateShardAdminRequest request);
}
