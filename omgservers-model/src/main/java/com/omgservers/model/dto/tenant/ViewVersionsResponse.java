package com.omgservers.model.dto.tenant;

import com.omgservers.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionsResponse {

    List<VersionModel> versions;
}
