package com.omgservers.schema.module.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.client.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientResponse {

    ClientModel client;
}
