package com.omgservers.service.entrypoint.admin.impl.service.webService;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request);

    Uni<BootstrapIndexServerResponse> bootstrapIndex(BootstrapIndexServerRequest request);
}
