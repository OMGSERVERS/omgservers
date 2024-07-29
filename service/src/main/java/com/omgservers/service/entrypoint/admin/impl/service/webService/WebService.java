package com.omgservers.service.entrypoint.admin.impl.service.webService;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenAdminResponse> createToken(CreateTokenAdminRequest request);
}
