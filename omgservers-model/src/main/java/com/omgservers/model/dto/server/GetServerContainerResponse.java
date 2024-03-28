
package com.omgservers.model.dto.server;

import com.omgservers.model.serverContainer.ServerContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServerContainerResponse {

    ServerContainerModel serverContainer;
}
