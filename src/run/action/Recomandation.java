package run.action;

import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Recomandation extends Action {

    public Recomandation(final int actionId) {
        super(actionId);
    }

    @Override
    public JSONObject work(Writer fileWriter) throws IOException {
        return null;
    }

}
