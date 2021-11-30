package user;

import visitor.Visitor;

public interface Visitable {
    /**
     * @param v
     * an accept method used in double dispatch
     */
    void accept(Visitor v);
}
