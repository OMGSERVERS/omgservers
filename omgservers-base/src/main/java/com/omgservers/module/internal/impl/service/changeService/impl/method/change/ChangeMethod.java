package com.omgservers.module.internal.impl.service.changeService.impl.method.change;

import com.omgservers.ChangeRequest;
import com.omgservers.ChangeResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeMethod {

    Uni<ChangeResponse> change(ChangeRequest request);
}
