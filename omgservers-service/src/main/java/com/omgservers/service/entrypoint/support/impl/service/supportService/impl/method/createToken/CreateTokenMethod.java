package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createToken;

import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenSupportResponse> createToken(CreateTokenSupportRequest request);
}
