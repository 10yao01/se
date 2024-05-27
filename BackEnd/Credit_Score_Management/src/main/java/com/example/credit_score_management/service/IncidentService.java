package com.example.credit_score_management.service;

import com.example.credit_score_management.pojo.Incident;

import java.util.List;

public interface IncidentService {
    List<Incident> list(String title, String name, Integer classes);

    void delete(Integer id);

    void add(Incident incident);

    void updateIsdeal(Integer id);
}
