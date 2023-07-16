package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConnectionHelpResponse {

    UUID connection;
}
