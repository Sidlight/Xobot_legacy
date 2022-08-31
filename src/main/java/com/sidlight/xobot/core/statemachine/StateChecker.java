package com.sidlight.xobot.core.statemachine;

import com.sidlight.xobot.core.statemachine.enums.BasicState;

import java.util.List;

public class StateChecker {

    private static final boolean isUnchecked = false;

    private static boolean havingAnEnd(StateTreeItem treeItem, List<StateTreeItem> allItem) {
        if (treeItem.getTargetState().equals(BasicState.WAITING_STATE)) return true;
        for (StateTreeItem item : allItem) {
            if (treeItem.getSourceState().equals(item.getTargetState())) return true;
        }
        return false;
    }

    public boolean checkStates(List<StateTreeItem> allItem) throws StateMachineException {
        if(isUnchecked)return true;
        for (StateTreeItem item : allItem) {
            if (!havingAnEnd(item, allItem)) {
                throw new StateMachineException("No transition to initial state in " + item, null);
            }
            int itemRepeatCount = 0;
            for (StateTreeItem item2 : allItem) {
                if (item2.equals(item)) {
                    itemRepeatCount++;
                }
            }
            if (itemRepeatCount > 1)
                throw new StateMachineException("Two identical transitions " + item, null);
        }
        return true;
    }


}
