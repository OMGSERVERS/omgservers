package com.omgservers.dispatcher.service.service.impl.method;

import com.omgservers.dispatcher.service.service.dto.CreateTokenRequest;
import com.omgservers.dispatcher.service.service.dto.CreateTokenResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenResponse> execute(CreateTokenRequest request);
}
