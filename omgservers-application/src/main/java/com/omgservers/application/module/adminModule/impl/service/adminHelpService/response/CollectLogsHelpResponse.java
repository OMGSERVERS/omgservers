package com.omgservers.application.module.adminModule.impl.service.adminHelpService.response;

import com.omgservers.application.module.adminModule.model.ServerLogModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectLogsHelpResponse {

    int totalRows;
    List<ServerLogModel> logs;
}
