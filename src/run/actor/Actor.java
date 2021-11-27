package run.actor;

import actor.ActorsAwards;

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


    public Actor(final String name, final String description, final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.description = description;
        this.filmography = filmography;
        this.awards = awards;
        this.rate = new HashMap<>();
        this.average = 0;
    }

    public String getName() {
        return name;
    }

    public double getAverage() {
        return average;
    }

    public String getDescription() {
        return description;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public int getAwardsNumber() {
        int count = 0;
        for (ActorsAwards a : awards.keySet()) {
            count += awards.get(a);
        }
        return count;
    }

    public void calculateGrade(String title, double grade) {
        rate.put(title, grade);
        System.out.println("Name " + name);
        System.out.print(average + " -> ");
        average = 0;

        for(String key : rate.keySet()) {
            average += rate.get(key);
        }
        average = average / rate.size();
        System.out.println(average);
    }

    @Override
    public String toString() {
        return name;
    }
}
