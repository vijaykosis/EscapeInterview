package com.example.EscapeInterview.service;

import com.example.EscapeInterview.model.Distance;

import java.util.Collection;

public interface DistanceCalculateService {

    public Collection<Distance> checkDistance(String city) throws Exception;
}
