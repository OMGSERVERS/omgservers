package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionMatchmaker;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMatchmakerMethod {

    Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(GetVersionMatchmakerRequest request);
}
