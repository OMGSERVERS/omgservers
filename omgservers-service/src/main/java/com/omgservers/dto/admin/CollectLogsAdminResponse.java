package com.omgservers.dto.admin;

import com.omgservers.model.serverLog.ServerLogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectLogsAdminResponse {

    int totalRows;
    List<ServerLogModel> logs;
}
