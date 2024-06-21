package com.omgservers.model.event;

public enum EventStatusEnum {

    /**
     * Event was only created.
     */
    CREATED,

    /**
     * Event was relayed from database to message queue successfully.
     */
    RELAYED,

    /**
     * Event was handled by handler successfully.
     */
    HANDLED,

    /**
     * Handling of event was failed.
     */
    FAILED
}
