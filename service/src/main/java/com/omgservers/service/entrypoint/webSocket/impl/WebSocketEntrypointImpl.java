package com.omgservers.service.entrypoint.webSocket.impl;

import com.omgservers.service.entrypoint.webSocket.WebSocketEntrypoint;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.WebSocketService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebSocketEntrypointImpl implements WebSocketEntrypoint {

    final WebSocketService webSocketService;
}
