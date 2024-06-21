package com.omgservers.service.entrypoint.admin.impl.service.webService;

import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenAdminResponse> createToken(CreateTokenAdminRequest request);
}
