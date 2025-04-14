package com.omgservers.schema.shard.pool.pool;

import com.omgservers.schema.model.pool.PoolModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolResponse {

    PoolModel pool;
}
