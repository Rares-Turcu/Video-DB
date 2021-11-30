package action;

import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public abstract class Action {
    private final int actionId;

    public Action(final int actionId) {
        this.actionId = actionId;
    }
    /**
     * @param fileWriter for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public abstract JSONObject work(Writer fileWriter) throws IOException;

    /**
     * @return actionId
     */
    public int getActionId() {
        return actionId;
    }
}
