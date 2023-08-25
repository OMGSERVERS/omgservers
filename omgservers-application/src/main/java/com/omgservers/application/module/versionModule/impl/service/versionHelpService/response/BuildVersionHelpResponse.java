package com.omgservers.application.module.versionModule.impl.service.versionHelpService.response;

import com.omgservers.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionHelpResponse {

    VersionModel version;
}
