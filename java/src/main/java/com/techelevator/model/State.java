package com.techelevator.model;

public class State {

    private String abbreviation;
    private String name;
    private int population;
    private int area;
    private double salesTax;
    private String nickname;
    private String censusRegion;

    public State(String abbreviation, String name, int population, int area, double salesTax, String nickname, String censusRegion) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.population = population;
        this.area = area;
        this.salesTax = salesTax;
        this.nickname = nickname;
        this.censusRegion = censusRegion;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getArea() {
        return area;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCensusRegion() {
        return censusRegion;
    }
}
