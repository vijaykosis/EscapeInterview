package com.example.EscapeInterview.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class City implements Serializable {

    private String id;

    private String name;

    private Location location;

    private String countryName;

    private String iata;

    private int rank;

    private String countryId;

    private String dest;

    private List<String> airports;

    private List<String> images;

    private double popularity;

    private String regId;

    private String contId;

    private String subId;

    private String terId;

    private int con;



    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIata() {
        return this.iata;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDest() {
        return this.dest;
    }

    public void setAirports(List<String> airports) {
        this.airports = airports;
    }

    public List<String> getAirports() {
        return this.airports;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegId() {
        return this.regId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String getContId() {
        return this.contId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubId() {
        return this.subId;
    }

    public void setTerId(String terId) {
        this.terId = terId;
    }

    public String getTerId() {
        return this.terId;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public int getCon() {
        return this.con;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return rank == city.rank && Double.compare(city.popularity, popularity) == 0 && con == city.con && Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(location, city.location) && Objects.equals(countryName, city.countryName) && Objects.equals(iata, city.iata) && Objects.equals(countryId, city.countryId) && Objects.equals(dest, city.dest) && Objects.equals(airports, city.airports) && Objects.equals(images, city.images) && Objects.equals(regId, city.regId) && Objects.equals(contId, city.contId) && Objects.equals(subId, city.subId) && Objects.equals(terId, city.terId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, countryName, iata, rank, countryId, dest, airports, images, popularity, regId, contId, subId, terId, con);
    }
}