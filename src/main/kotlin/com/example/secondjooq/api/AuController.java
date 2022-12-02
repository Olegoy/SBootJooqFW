package com.example.secondjooq.api;

import java.util.List;

import com.example.secondjooq.model.Au;
import com.example.secondjooq.service.AuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/au")
public class AuController {

    private final AuService auService;

    public AuController(AuService auService) {
        this.auService = auService;
    }

    @GetMapping("/{id}")
    public List<Au> getById(@PathVariable Integer id) {
        return auService.findById(id);
    }
}
