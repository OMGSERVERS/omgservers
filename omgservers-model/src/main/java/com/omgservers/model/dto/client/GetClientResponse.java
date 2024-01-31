package com.omgservers.model.dto.client;

import com.omgservers.model.client.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientResponse {

    ClientModel client;
}
