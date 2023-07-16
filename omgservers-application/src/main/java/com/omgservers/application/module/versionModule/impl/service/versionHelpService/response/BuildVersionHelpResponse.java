package com.omgservers.application.module.versionModule.impl.service.versionHelpService.response;

import com.omgservers.application.module.versionModule.model.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionHelpResponse {

    VersionModel version;
}
