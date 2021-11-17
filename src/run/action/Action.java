package run.action;

import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public abstract class Action {
    private final int actionId;

    public Action(final int actionId) {
        this.actionId = actionId;
    }

    public abstract JSONObject work(Writer fileWriter) throws IOException;

    public int getActionId() {
        return actionId;
    }

    @Override
    public String toString() {
        return null;
    }
}
