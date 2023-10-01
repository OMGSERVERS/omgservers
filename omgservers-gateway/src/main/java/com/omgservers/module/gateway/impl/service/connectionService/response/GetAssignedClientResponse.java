package com.omgservers.module.gateway.impl.service.connectionService.response;

import com.omgservers.model.assignedClient.AssignedClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignedClientResponse {

    AssignedClientModel assignedClient;
}
