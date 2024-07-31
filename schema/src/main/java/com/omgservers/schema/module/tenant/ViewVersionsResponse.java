package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.version.VersionProjectionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionsResponse {

    List<VersionProjectionModel> versionProjections;
}
