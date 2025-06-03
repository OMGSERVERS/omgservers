package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl;

import com.omgservers.connector.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnCloseEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnErrorEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnOpenEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnTextMessageEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.EntrypointService;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method.OnBinaryMessageMethod;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method.OnCloseMethod;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method.OnErrorMethod;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method.OnOpenMethod;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method.OnTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EntrypointServiceImpl implements EntrypointService {

    final OnBinaryMessageMethod onBinaryMessageMethod;
    final OnTextMessageMethod onTextMessageMethod;
    final OnCloseMethod onCloseMethod;
    final OnErrorMethod onErrorMethod;
    final OnOpenMethod onOpenMethod;

    @Override
    public Uni<Void> onOpen(@Valid final OnOpenEntrypointRequest request) {
        return onOpenMethod.execute(request);
    }

    @Override
    public Uni<Void> onClose(@Valid final OnCloseEntrypointRequest request) {
        return onCloseMethod.execute(request);
    }

    @Override
    public Uni<Void> onError(@Valid final OnErrorEntrypointRequest request) {
        return onErrorMethod.execute(request);
    }

    @Override
    public Uni<Void> onTextMessage(@Valid final OnTextMessageEntrypointRequest request) {
        return onTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> onBinaryMessage(@Valid final OnBinaryMessageEntrypointRequest request) {
        return onBinaryMessageMethod.execute(request);
    }
}
