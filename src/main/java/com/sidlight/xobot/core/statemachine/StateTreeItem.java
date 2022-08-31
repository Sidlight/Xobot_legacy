package com.sidlight.xobot.core.statemachine;

import com.sidlight.xobot.core.statemachine.enums.BasicState;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class StateTreeItem {
    @Getter
    private String sourceState;

    @Getter
    private String targetState;
    @Getter
    private String event;
    public StateTreeItem(String sourceState,String targetState, String event) {
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.event = event;
    }

    @Override
    public String toString() {
        return "StateTreeItem{" +
                "sourceState='" + sourceState + '\'' +
                ", targetState='" + targetState + '\'' +
                ", event='" + event + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateTreeItem item = (StateTreeItem) o;
        return Objects.equals(sourceState, item.sourceState) && Objects.equals(targetState, item.targetState) && Objects.equals(event, item.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceState, targetState, event);
    }

}
