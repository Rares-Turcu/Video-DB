package actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Actor {
    private final String name;
    private final String description;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    private final Map<String, Double> rate;
    private double average;


    public Actor(final String name, final String description,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.description = description;
        this.filmography = filmography;
        this.awards = awards;
        this.rate = new HashMap<>();
        this.average = 0;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return average
     */
    public double getAverage() {
        return average;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return awards
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * @return number of awards from the hash map
     */
    public int getAwardsNumber() {
        int count = 0;
        for (ActorsAwards a : awards.keySet()) {
            count += awards.get(a);
        }
        return count;
    }

    /**
     * @param  title
     * @param grade
     * the method updates the total grade of an actor
     * when a show he played in was reviewed by a user
     */
    public void calculateGrade(final String title, final double grade) {
        rate.put(title, grade);
        average = 0;

        for (String key : rate.keySet()) {
            average += rate.get(key);
        }
        average = average / rate.size();
    }

    /**
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
