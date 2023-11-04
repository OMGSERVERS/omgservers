package com.omgservers.service.module.user.impl.service.tokenService;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TokenService {

    Uni<CreateTokenResponse> createToken(@Valid CreateTokenRequest request);

    Uni<IntrospectTokenResponse> introspectToken(@Valid IntrospectTokenRequest request);
}
