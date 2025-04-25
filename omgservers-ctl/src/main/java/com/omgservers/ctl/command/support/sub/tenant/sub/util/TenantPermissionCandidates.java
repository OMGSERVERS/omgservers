package com.omgservers.ctl.command.support.sub.tenant.sub.util;

import com.omgservers.ctl.dto.permission.TenantPermissionEnum;

import java.util.Arrays;
import java.util.Iterator;

public class TenantPermissionCandidates implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(TenantPermissionEnum.values())
                .map(TenantPermissionEnum::getPermission)
                .iterator();
    }
}
