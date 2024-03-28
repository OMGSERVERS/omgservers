package com.omgservers.model.dto.server;

import com.omgservers.model.serverContainer.ServerContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewServerContainersResponse {

    List<ServerContainerModel> serverContainers;
}
