package TheGreedyStickFigure.mvc.model;

import java.util.ArrayList;

/**
 * Created by kevinhawryluk on 11/30/16. This is used to store the points for the Movable_Person images.
 */
public class PointsArrayList extends ArrayList {
    private String name;

    /**
     * Instantiates a new Points array list.
     *
     * @param name the name
     */
    public PointsArrayList(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    public boolean equals(PointsArrayList o) {
        if(this.name.equals(o.name))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
