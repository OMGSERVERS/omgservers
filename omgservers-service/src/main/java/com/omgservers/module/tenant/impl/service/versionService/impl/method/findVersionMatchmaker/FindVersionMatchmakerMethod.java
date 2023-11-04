package com.omgservers.module.tenant.impl.service.versionService.impl.method.findVersionMatchmaker;

import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionMatchmakerMethod {
    Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(FindVersionMatchmakerRequest request);
}
