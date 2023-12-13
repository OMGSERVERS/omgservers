package com.omgservers.model.dto.runtime;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRuntimeClientsResponse {

    List<RuntimeClientModel> runtimeClients;
}
