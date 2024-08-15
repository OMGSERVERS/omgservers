package com.omgservers.service.service.registry.impl;

import com.omgservers.service.service.registry.dto.IssueTokenRequest;
import com.omgservers.service.service.registry.dto.IssueTokenResponse;
import com.omgservers.service.service.registry.impl.method.IssueTokenMethod;
import com.omgservers.service.service.registry.RegistryService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RegistryServiceImpl implements RegistryService {

    final IssueTokenMethod issueTokenMethod;

    @Override
    public Uni<IssueTokenResponse> issueToken(@Valid final IssueTokenRequest request) {
        return issueTokenMethod.issueToken(request);
    }
}
