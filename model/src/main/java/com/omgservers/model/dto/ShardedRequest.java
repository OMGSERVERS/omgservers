package com.omgservers.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ShardedRequest {

    @JsonIgnore
    String getRequestShardKey();
}
