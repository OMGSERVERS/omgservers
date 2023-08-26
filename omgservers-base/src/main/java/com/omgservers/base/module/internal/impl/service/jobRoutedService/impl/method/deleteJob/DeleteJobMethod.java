package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.deleteJob;

import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request);
}
