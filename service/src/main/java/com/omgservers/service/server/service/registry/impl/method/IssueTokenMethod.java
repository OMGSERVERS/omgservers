package com.omgservers.service.server.service.registry.impl.method;

import com.omgservers.schema.service.registry.IssueTokenRequest;
import com.omgservers.schema.service.registry.IssueTokenResponse;
import io.smallrye.mutiny.Uni;

public interface IssueTokenMethod {
    Uni<IssueTokenResponse> issueToken(IssueTokenRequest request);
}
