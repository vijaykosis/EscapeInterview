package com.example.EscapeInterview.helper;

import com.example.EscapeInterview.controller.DistanceCalculateController;
import com.example.EscapeInterview.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class DistanceServiceHelper {
    private static final Logger logger = LoggerFactory.getLogger(DistanceCalculateController.class);

    public String readJsonFile(String fileName) {
        logger.info("read Json file{}",fileName);
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (String) reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found" + fileName);
        }
    }

    public InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = DistanceServiceHelper.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }


    public int calculateDistanceInKilometer(double lat1, double lon1,
                                            double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return (int) (Math.round(rad * c));
    }

    public ConcurrentMap<String, List<City>> prepareCitiesMap(Map<String, City> response) {
        ConcurrentMap<String, List<City>> citiesMap = new ConcurrentHashMap<>();
        List<City> cityList = new ArrayList<>();
        for (Map.Entry<String, City> entry : response.entrySet()) {
            City jsonNode = entry.getValue();
            System.out.println(jsonNode.getLocation());
            System.out.println(jsonNode.getContId());
            if (citiesMap.containsKey(jsonNode.getContId())) {
                cityList = citiesMap.get(jsonNode.getContId());
                cityList.add(jsonNode);
            } else {
                cityList = new ArrayList<>();
                cityList.add(jsonNode);
                citiesMap.put(jsonNode.getContId(), cityList);
            }
        }
        return citiesMap;
    }
}
