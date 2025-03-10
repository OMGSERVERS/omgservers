package com.omgservers.service.event;

public enum EventStatusEnum {

    /**
     * The event was just created.
     */
    CREATED,

    /**
     * The event was processed successfully.
     */
    PROCESSED,

    /**
     * The event processing failed.
     */
    FAILED,
}
