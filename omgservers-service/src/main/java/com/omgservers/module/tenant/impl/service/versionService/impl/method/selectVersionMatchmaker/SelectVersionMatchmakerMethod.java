package com.omgservers.module.tenant.impl.service.versionService.impl.method.selectVersionMatchmaker;

import com.omgservers.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SelectVersionMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SelectVersionMatchmakerMethod {
    Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(SelectVersionMatchmakerRequest request);
}
