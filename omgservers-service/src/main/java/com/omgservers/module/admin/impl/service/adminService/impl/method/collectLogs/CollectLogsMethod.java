package com.omgservers.module.admin.impl.service.adminService.impl.method.collectLogs;

import com.omgservers.dto.admin.CollectLogsAdminRequest;
import com.omgservers.dto.admin.CollectLogsAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CollectLogsMethod {
    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);
}
