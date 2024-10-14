package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService;

import com.omgservers.service.entrypoint.dispatcher.dto.OnBinaryMessageDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnCloseDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnErrorDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnOpenDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnTextMessageDispatcherRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<Void> onOpen(@Valid OnOpenDispatcherRequest request);

    Uni<Void> onClose(@Valid OnCloseDispatcherRequest request);

    Uni<Void> onError(@Valid OnErrorDispatcherRequest request);

    Uni<Void> onTextMessage(@Valid OnTextMessageDispatcherRequest request);

    Uni<Void> onBinaryMessage(@Valid OnBinaryMessageDispatcherRequest request);
}
