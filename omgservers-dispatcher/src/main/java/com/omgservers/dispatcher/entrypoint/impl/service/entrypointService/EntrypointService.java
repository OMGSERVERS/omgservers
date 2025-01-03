package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.impl.dto.OnCloseEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.impl.dto.OnErrorEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.impl.dto.OnOpenEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.impl.dto.OnTextMessageEntrypointRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EntrypointService {

    Uni<Void> onOpen(@Valid OnOpenEntrypointRequest request);

    Uni<Void> onClose(@Valid OnCloseEntrypointRequest request);

    Uni<Void> onError(@Valid OnErrorEntrypointRequest request);

    Uni<Void> onTextMessage(@Valid OnTextMessageEntrypointRequest request);

    Uni<Void> onBinaryMessage(@Valid OnBinaryMessageEntrypointRequest request);
}
