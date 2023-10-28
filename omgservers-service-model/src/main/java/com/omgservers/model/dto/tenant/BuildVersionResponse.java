package com.omgservers.model.dto.tenant;

import com.omgservers.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionResponse {

    VersionModel version;
}
