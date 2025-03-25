package com.omgservers.schema.module.pool.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolCommandResponse {

    List<PoolCommandModel> poolCommands;
}
