package action;

public abstract class Queries extends Action {
    private final int number;
    private final String sortType;

    public Queries(final int actionId, final int number, final String sortType) {
        super(actionId);
        this.number = number;
        this.sortType = sortType;
    }

    /**
     * @return the type of sorting
     */
    public String getSortType() {
        return sortType;
    }

    /**
     * @return the number of results
     */
    public int getNumber() {
        return number;
    }
}
