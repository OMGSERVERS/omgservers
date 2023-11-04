package com.omgservers.service.module.gateway.impl.service.connectionService.response;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSessionResponse {

    Session session;
}
