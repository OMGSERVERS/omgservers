package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.entrypoint.developer.dto.StageDashboardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageDashboardDeveloperResponse {

    StageDashboardDto stageDashboard;
}
