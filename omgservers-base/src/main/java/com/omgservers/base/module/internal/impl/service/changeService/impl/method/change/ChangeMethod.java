package com.omgservers.base.module.internal.impl.service.changeService.impl.method.change;

import com.omgservers.dto.internalModule.ChangeRequest;
import com.omgservers.dto.internalModule.ChangeResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeMethod {

    Uni<ChangeResponse> change(ChangeRequest request);
}
