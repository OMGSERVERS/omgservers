package com.omgservers.schema.shard.pool.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolCommandResponse {

    PoolCommandModel poolCommand;
}
