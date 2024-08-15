package com.omgservers.service.service.registry.impl.method;

import com.omgservers.service.service.registry.dto.IssueTokenRequest;
import com.omgservers.service.service.registry.dto.IssueTokenResponse;
import io.smallrye.mutiny.Uni;

public interface IssueTokenMethod {
    Uni<IssueTokenResponse> issueToken(IssueTokenRequest request);
}
