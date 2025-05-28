package com.omgservers.ctl.command.config.sub.installation.sub.util;

import com.omgservers.ctl.dto.region.RegionEnum;

import java.util.Arrays;
import java.util.Iterator;

public class RegionCandidates implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(RegionEnum.values())
                .map(RegionEnum::getRegion)
                .iterator();
    }
}
