package com.example.secondjooq.service;

import java.util.List;

import com.example.secondjooq.model.Au;
import com.example.secondjooq.repository.ARep;
import org.springframework.stereotype.Service;

@Service
public class AuService {

    private final ARep rep;

    public AuService(ARep rep) {
        this.rep = rep;
    }

    public List<Au> findAll() {
        return rep.find();
    }

    public List<Au> findById(Integer id) {
        return rep.findById(id);
    }
}
