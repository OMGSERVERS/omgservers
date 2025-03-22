package com.omgservers.service.service.registry.impl.method;

import com.omgservers.service.service.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.service.registry.dto.IssueRegistryTokensResponse;
import io.smallrye.mutiny.Uni;

public interface IssueRegistryTokensMethod {
    Uni<IssueRegistryTokensResponse> execute(IssueRegistryTokensRequest request);
}
