package com.omgservers.service.entrypoint.server.impl.service.webService;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request);
}
