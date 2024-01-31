
package com.omgservers.model.dto.client;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientRuntimeResponse {

    ClientRuntimeModel clientRuntime;
}
