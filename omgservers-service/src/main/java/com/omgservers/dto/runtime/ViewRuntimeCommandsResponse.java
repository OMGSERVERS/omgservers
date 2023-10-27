package com.omgservers.dto.runtime;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
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
