package run.action;

import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Queries extends Action {
    private final int number;
    private final String sort_type;

    public Queries(int actionId, int number, String sort_type) {
        super(actionId);
        this.number = number;
        this.sort_type = sort_type;
    }

    public String getSort_type() {
        return sort_type;
    }

    public int getNumber() {
        return number;
    }
}
