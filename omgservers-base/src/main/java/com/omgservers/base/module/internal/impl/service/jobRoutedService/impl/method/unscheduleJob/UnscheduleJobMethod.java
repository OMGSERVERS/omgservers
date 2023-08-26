package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.unscheduleJob;

import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface UnscheduleJobMethod {
    Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request);
}
