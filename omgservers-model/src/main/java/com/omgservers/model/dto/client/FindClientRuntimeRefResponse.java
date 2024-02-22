package com.omgservers.model.dto.client;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindClientRuntimeRefResponse {

    ClientRuntimeRefModel clientRuntimeRef;
}
