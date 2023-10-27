package com.omgservers.dto.tenant;

import com.omgservers.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionResponse {

    VersionModel version;
}
