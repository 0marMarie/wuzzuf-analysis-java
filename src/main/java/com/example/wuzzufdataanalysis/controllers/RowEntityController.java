package com.example.wuzzufdataanalysis.controllers;


import com.example.wuzzufdataanalysis.repositories.RowEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RowEntityController {
    private final RowEntityRepository rowEntityRepository;

    public RowEntityController(RowEntityRepository rowEntityRepository) {
        this.rowEntityRepository = rowEntityRepository;
    }

    @RequestMapping("/")
    public String start(){
        return "index";
    }

    @RequestMapping("/display-data")
    public String getData(Model model){
        model.addAttribute("rows", rowEntityRepository.findAll());
        return "sample";
    }
}
