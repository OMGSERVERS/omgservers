package com.omgservers.module.internal.impl.service.changeService.impl.method.change;

import com.omgservers.dto.internal.ChangeRequest;
import com.omgservers.dto.internal.ChangeResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeMethod {

    Uni<ChangeResponse> change(ChangeRequest request);
}
