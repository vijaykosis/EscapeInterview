package com.example.EscapeInterview.service.impl;

import com.example.EscapeInterview.controller.DistanceCalculateController;
import com.example.EscapeInterview.helper.Constant;
import com.example.EscapeInterview.helper.DistanceServiceHelper;
import com.example.EscapeInterview.model.City;
import com.example.EscapeInterview.model.Distance;
import com.example.EscapeInterview.service.DistanceCalculateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class DistanceCalculateServiceImpl implements DistanceCalculateService {
    private static final Logger logger = LoggerFactory.getLogger(DistanceCalculateController.class);


    @Autowired
    private DistanceServiceHelper helper;

    @Override
    public Collection<Distance> checkDistance(String cityName) throws Exception {
        logger.info("request at service layer{}");
        String json = helper.readJsonFile(Constant.FILE_NAME);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

            Map<String, City> response = new ObjectMapper().readValue(json, new TypeReference<Map<String, City>>() {
            });
            ConcurrentMap<String, List<City>> citiesMap = helper.prepareCitiesMap(response);
            City city = findSelectedCity(citiesMap, cityName);
            if (city == null) {
                logger.info("Record not found for given key{}", cityName);
                throw new Exception("Record not found for given key" + cityName);
            }
            Collection<Distance> result = checkSmallestDistance(city, citiesMap);
            if (result != null && !result.isEmpty() && result.size() > 0)
                return result;
            else
                throw new Exception("Record not found for given key");

        } catch (Exception e) {
            logger.info("Exception  at service layer{}", e);
            throw new Exception("file not found");
        }
    }

    private Collection<Distance> checkSmallestDistance(City city1, ConcurrentMap<String, List<City>> citiesMap) throws Exception {
        Map<String, List<Distance>> citiesMapping = new ConcurrentHashMap<>();
        for (Map.Entry<String, List<City>> entry : citiesMap.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(city1.getContId())) {
                List<City> jsonNode = entry.getValue();
                Map<String, List<Distance>> distanceMapping = calculateDistance(city1, jsonNode, entry.getKey());
                citiesMapping.putAll(distanceMapping);
            }
        }
        return prepareResponse(citiesMapping);
    }

    private Collection<Distance> prepareResponse(Map<String, List<Distance>> citiesMapping) throws Exception {
        HashMap<String, Distance> treeMap = new HashMap<String, Distance>();
        for (Map.Entry<String, List<Distance>> entry : citiesMapping.entrySet()) {
            List<Distance> jsonNode = entry.getValue();
            Distance km = findMinDistanceValue(jsonNode);
            treeMap.put(entry.getKey(), km);
        }

        // Create a list from elements of HashMap
        List<Map.Entry<String, Distance>> list =
                new LinkedList<Map.Entry<String, Distance>>(treeMap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Distance>>() {
            public int compare(Map.Entry<String, Distance> o1,
                               Map.Entry<String, Distance> o2) {
                return (o1.getValue().getKm() - o2.getValue().getKm());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Distance> sortedCitiesMap = new LinkedHashMap<String, Distance>();
        for (Map.Entry<String, Distance> aa : list) {
            sortedCitiesMap.put(aa.getKey(), aa.getValue());
        }
        return sortedCitiesMap.values();
    }

    private Distance findMinDistanceValue(List<Distance> distanceList) {
        Comparator<Distance> comparator = (p1, p2) -> p1.getKm() - p2.getKm();
        Collections.sort(distanceList, comparator);
        return distanceList.get(0);
    }

    private Map<String, List<Distance>> calculateDistance(City startingPoint, List<City> jsonNode, String key) {
        Map<String, List<Distance>> cities = new ConcurrentHashMap<>();
        List<Distance> distanceList = new ArrayList<>();

        for (City city : jsonNode) {
            int km = helper.calculateDistanceInKilometer(startingPoint.getLocation().getLat(), startingPoint.getLocation().getLon(), city.getLocation().getLat(), city.getLocation().getLon());
            Distance distance = new Distance();
            distance.setKm(km);
            distance.setCountryName(city.getCountryName());
            distance.setCountryId(city.getCountryId());
            distance.setName(city.getName());
            distance.setContId(city.getContId());
            distance.setCon(city.getCon());
            distance.setRank(city.getRank());
            distance.setLocation(city.getLocation());
            distance.setAirports(city.getAirports());
            distance.setImages(city.getImages());
            distance.setDest(city.getDest());
            distance.setPopularity(city.getPopularity());
            distance.setRegId(city.getRegId());
            distance.setId(city.getId());
            distanceList.add(distance);
        }
        if (distanceList.size() > 0) {
            cities.put(key, distanceList);
        }

        return cities;
    }

    private City findSelectedCity(ConcurrentMap<String, List<City>> citiesMap, String cityName) {
        Map<String, String> citiesMapping = new HashMap<>();
        City city = null;
        for (Map.Entry<String, List<City>> entry : citiesMap.entrySet()) {
            List<City> jsonNode = entry.getValue();
            city = findCity(cityName, jsonNode);
            if (city != null) {
                return city;
            }
        }
        return city;
    }

    public static City findCity(String name, List<City> cityList) {
        for (City city : cityList) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }
}
