package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.player.PlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerDeletedEventBodyModel extends EventBodyModel {

    PlayerModel player;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PLAYER_DELETED;
    }

    @Override
    public Long getGroupId() {
        return player.getUserId();
    }
}
