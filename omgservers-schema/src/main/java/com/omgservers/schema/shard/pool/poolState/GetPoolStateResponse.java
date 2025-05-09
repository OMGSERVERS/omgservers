package com.omgservers.schema.shard.pool.poolState;

import com.omgservers.schema.model.poolState.PoolStateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolStateResponse {

    PoolStateDto poolState;
}
