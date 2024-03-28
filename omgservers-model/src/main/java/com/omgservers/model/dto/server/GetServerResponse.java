package com.omgservers.model.dto.server;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.server.ServerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServerResponse {

    ServerModel server;
}
