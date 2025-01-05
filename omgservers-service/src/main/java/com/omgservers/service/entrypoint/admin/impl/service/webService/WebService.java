package com.omgservers.service.entrypoint.admin.impl.service.webService;

import com.omgservers.schema.entrypoint.admin.BcryptHashAdminRequest;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminResponse;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminRequest;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenAdminResponse> execute(CreateTokenAdminRequest request);

    Uni<GenerateIdAdminResponse> execute(GenerateIdAdminRequest request);

    Uni<BcryptHashAdminResponse> execute(BcryptHashAdminRequest request);

    Uni<CalculateShardAdminResponse> execute(CalculateShardAdminRequest request);

    Uni<PingDockerHostAdminResponse> execute(PingDockerHostAdminRequest request);
}
