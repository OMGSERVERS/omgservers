package com.omgservers.base.impl.service.eventHelpService.impl.method.fireEventMethod;

import com.omgservers.base.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.base.impl.service.eventHelpService.response.FireEventHelpResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventHelpResponse> fireEvent(FireEventHelpRequest request);
}
