package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.selectVersionMatchmaker;

import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SelectVersionMatchmakerMethod {
    Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(SelectVersionMatchmakerRequest request);
}
