package com.omgservers;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChangeContext<T> {

    final Context context;
    final List<EventModel> changeEvents;
    final List<LogModel> changeLogs;

    T result;

    public ChangeContext(Context context) {
        this.context = context;
        changeEvents = new ArrayList<>();
        changeLogs = new ArrayList<>();

        context.put("events", changeEvents);
        context.put("logs", changeLogs);
    }

    public List<EventModel> getChangeEvents() {
        return changeEvents;
    }

    public List<LogModel> getChangeLogs() {
        return changeLogs;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void add(EventModel changeEvent) {
        changeEvents.add(changeEvent);
        log.info("Event was added, event={}", changeEvent);
    }

    public void add(LogModel changeLog) {
        changeLogs.add(changeLog);
        log.info("Log was inserted, log={}", changeLog);
    }
}
