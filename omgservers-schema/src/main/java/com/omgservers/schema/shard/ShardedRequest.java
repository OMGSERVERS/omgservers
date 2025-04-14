package com.omgservers.schema.shard;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ShardedRequest {

    @JsonIgnore
    String getRequestShardKey();
}
