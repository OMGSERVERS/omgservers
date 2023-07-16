package com.omgservers.application.module.userModule.impl.service.clientInternalService.response;

import com.omgservers.application.module.userModule.model.client.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientInternalResponse {

    ClientModel client;
}
