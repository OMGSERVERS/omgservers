package com.omgservers.ctl.command.support.sub.stage.sub.util;

import com.omgservers.ctl.dto.permission.StagePermissionEnum;

import java.util.Arrays;
import java.util.Iterator;

public class StagePermissionCandidates implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(StagePermissionEnum.values())
                .map(StagePermissionEnum::getPermission)
                .iterator();
    }
}
