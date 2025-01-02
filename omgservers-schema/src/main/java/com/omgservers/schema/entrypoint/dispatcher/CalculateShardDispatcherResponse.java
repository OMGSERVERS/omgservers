package com.omgservers.schema.entrypoint.dispatcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateShardDispatcherResponse {

    Integer shardIndex;
    URI serverUri;
    Boolean foreign;
}
