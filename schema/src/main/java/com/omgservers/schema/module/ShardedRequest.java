package com.omgservers.schema.module;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ShardedRequest {

    @JsonIgnore
    String getRequestShardKey();
}
