package com.omgservers.schema.shard.runtime.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRuntimeCommandsResponse {

    List<RuntimeCommandModel> runtimeCommands;
}
