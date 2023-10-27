package com.omgservers.module.user.impl.service.tokenService;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TokenService {

    Uni<CreateTokenResponse> createToken(@Valid CreateTokenRequest request);

    Uni<IntrospectTokenResponse> introspectToken(@Valid IntrospectTokenRequest request);
}
