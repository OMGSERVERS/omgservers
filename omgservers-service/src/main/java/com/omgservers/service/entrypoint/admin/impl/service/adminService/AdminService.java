package com.omgservers.service.entrypoint.admin.impl.service.adminService;

import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface AdminService {

    Uni<CreateTokenAdminResponse> execute(@Valid CreateTokenAdminRequest request);

    Uni<CalculateShardAdminResponse> execute(@Valid CalculateShardAdminRequest request);

    Uni<PingDockerHostAdminResponse> execute(@Valid PingDockerHostAdminRequest request);
}
