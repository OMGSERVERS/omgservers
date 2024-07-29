package com.omgservers.schema.module.tenant.versionImageRef;

import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionImageRefResponse {

    VersionImageRefModel versionImageRef;
}
