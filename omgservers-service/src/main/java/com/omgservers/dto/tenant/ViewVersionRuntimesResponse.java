package com.omgservers.dto.tenant;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionRuntimesResponse {

    List<VersionRuntimeModel> versionRuntimes;
}
