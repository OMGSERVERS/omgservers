package com.omgservers.ctl.command.support.sub.project.sub.util;

import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;

import java.util.Arrays;
import java.util.Iterator;

public class ProjectPermissionCandidates implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(ProjectPermissionEnum.values())
                .map(ProjectPermissionEnum::getPermission)
                .iterator();
    }
}
