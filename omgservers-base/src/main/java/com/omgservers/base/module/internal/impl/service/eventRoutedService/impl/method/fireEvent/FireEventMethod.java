package com.omgservers.base.module.internal.impl.service.eventRoutedService.impl.method.fireEvent;

import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request);
}
