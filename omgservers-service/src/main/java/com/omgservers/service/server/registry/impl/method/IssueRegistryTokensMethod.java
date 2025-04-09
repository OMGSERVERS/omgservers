package com.omgservers.service.server.registry.impl.method;

import com.omgservers.service.server.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.server.registry.dto.IssueRegistryTokensResponse;
import io.smallrye.mutiny.Uni;

public interface IssueRegistryTokensMethod {
    Uni<IssueRegistryTokensResponse> execute(IssueRegistryTokensRequest request);
}
