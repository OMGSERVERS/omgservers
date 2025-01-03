package com.omgservers.dispatcher.module.impl.service.dispatcherService;

import com.omgservers.dispatcher.module.impl.dto.OnBinaryMessageRequest;
import com.omgservers.dispatcher.module.impl.dto.OnCloseRequest;
import com.omgservers.dispatcher.module.impl.dto.OnErrorRequest;
import com.omgservers.dispatcher.module.impl.dto.OnOpenRequest;
import com.omgservers.dispatcher.module.impl.dto.OnTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<Void> execute(@Valid OnOpenRequest request);

    Uni<Void> execute(@Valid OnCloseRequest request);

    Uni<Void> execute(@Valid OnErrorRequest request);

    Uni<Void> execute(@Valid OnTextMessageRequest request);

    Uni<Void> execute(@Valid OnBinaryMessageRequest request);
}
