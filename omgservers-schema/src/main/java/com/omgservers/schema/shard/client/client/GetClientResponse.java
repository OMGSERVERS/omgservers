package com.omgservers.schema.shard.client.client;

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
