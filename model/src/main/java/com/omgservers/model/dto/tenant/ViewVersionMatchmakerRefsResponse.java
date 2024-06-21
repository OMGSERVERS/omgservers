package com.omgservers.model.dto.tenant;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionMatchmakerRefsResponse {

    List<VersionMatchmakerRefModel> versionMatchmakerRefs;
}
