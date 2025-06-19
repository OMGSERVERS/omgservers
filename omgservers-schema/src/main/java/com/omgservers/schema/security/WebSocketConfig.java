package com.omgservers.schema.security;

import java.net.URI;

public record WebSocketConfig(URI connectionUrl, String secWebsocketProtocol) {
}
