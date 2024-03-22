
package com.omgservers.model.dto.client;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientMatchmakerRefResponse {

    ClientMatchmakerRefModel clientMatchmakerRef;
}
