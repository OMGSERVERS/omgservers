package com.omgservers.model.dto.client;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClientRuntimesResponse {

    List<ClientRuntimeModel> clientRuntimes;
}