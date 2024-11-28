package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import io.smallrye.mutiny.Uni;

public interface PingDockerHostMethod {
    Uni<PingDockerHostAdminResponse> execute(PingDockerHostAdminRequest request);
}
