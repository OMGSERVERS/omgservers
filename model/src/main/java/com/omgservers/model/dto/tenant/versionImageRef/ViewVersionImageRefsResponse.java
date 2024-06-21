package com.omgservers.model.dto.tenant.versionImageRef;

import com.omgservers.model.versionImageRef.VersionImageRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionImageRefsResponse {

    List<VersionImageRefModel> versionImageRefs;
}
