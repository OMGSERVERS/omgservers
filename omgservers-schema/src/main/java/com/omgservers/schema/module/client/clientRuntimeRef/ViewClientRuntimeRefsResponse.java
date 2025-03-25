package com.omgservers.schema.module.client.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
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
