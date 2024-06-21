package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createToken;

import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenSupportResponse> createToken(CreateTokenSupportRequest request);
}
