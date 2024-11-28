package com.omgservers.service.entrypoint.admin.impl.service.webService;

import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenAdminResponse> execute(CreateTokenAdminRequest request);

    Uni<CalculateShardAdminResponse> execute(CalculateShardAdminRequest request);

    Uni<PingDockerHostAdminResponse> execute(PingDockerHostAdminRequest request);
}
