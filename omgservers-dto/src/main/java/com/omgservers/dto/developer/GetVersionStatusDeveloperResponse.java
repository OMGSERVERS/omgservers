package com.omgservers.dto.developer;

import com.omgservers.model.version.VersionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionStatusDeveloperResponse {

    VersionStatusEnum status;
}
