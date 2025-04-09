package com.omgservers.service.server.registry.impl;

import com.omgservers.service.server.registry.RegistryService;
import com.omgservers.service.server.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.server.registry.dto.IssueRegistryTokensResponse;
import com.omgservers.service.server.registry.impl.method.IssueRegistryTokensMethod;
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

    final IssueRegistryTokensMethod issueRegistryTokensMethod;

    @Override
    public Uni<IssueRegistryTokensResponse> execute(@Valid final IssueRegistryTokensRequest request) {
        return issueRegistryTokensMethod.execute(request);
    }
}
