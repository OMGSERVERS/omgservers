package com.omgservers.base.module.internal.impl.service.eventRoutedService;

import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import io.smallrye.mutiny.Uni;

public interface EventRoutedService {

    Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request);
}
