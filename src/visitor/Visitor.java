package visitor;

import user.Premium;
import user.User;

public interface Visitor {
    /**
     * @param user
     * a visit method used in double dispatch
     */
    void visit(User user);

    /**
     * @param premium
     * a visit method used in double dispatch
     */
    void visit(Premium premium);
}
