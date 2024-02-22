package com.omgservers.model.dto.client;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClientRuntimeRefsResponse {

    List<ClientRuntimeRefModel> clientRuntimeRefs;
}
