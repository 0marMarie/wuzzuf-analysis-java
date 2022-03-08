package com.example.wuzzufdataanalysis.controllers;


import com.example.wuzzufdataanalysis.repositories.*;
import org.apache.spark.sql.Row;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RowEntityController {
    private final RowEntityRepository rowEntityRepository;
    private final SummaryEntityRepository summaryEntityRepository;
    private final CompanyJobsEntityRepository companyJobsEntityRepository;
    private final JobTitleEntityRepository jobTitleEntityRepository;
    private final cleanDataEntityRepository cleanDataEntityRepository;
    private final AreasEntityRepository areasEntityRepository;
    private final SkillsEntityRepository skillsEntityRepository;

    public RowEntityController(RowEntityRepository rowEntityRepository, SummaryEntityRepository summaryEntityRepository, CompanyJobsEntityRepository companyJobsEntityRepository, JobTitleEntityRepository jobTitleEntityRepository, com.example.wuzzufdataanalysis.repositories.cleanDataEntityRepository cleanDataEntityRepository, AreasEntityRepository areasEntityRepository, SkillsEntityRepository skillsEntityRepository) {
        this.rowEntityRepository = rowEntityRepository;
        this.summaryEntityRepository = summaryEntityRepository;
        this.companyJobsEntityRepository = companyJobsEntityRepository;
        this.jobTitleEntityRepository = jobTitleEntityRepository;
        this.cleanDataEntityRepository = cleanDataEntityRepository;
        this.areasEntityRepository = areasEntityRepository;
        this.skillsEntityRepository = skillsEntityRepository;
    }

    @RequestMapping("/")
    public String start(Model model){
        return "index";
    }

    @RequestMapping("/sample")
    public String getData(Model model){
        model.addAttribute("rows", rowEntityRepository.findAll());
        return "sample";
    }

    // There is no structue ?!
    @RequestMapping("/summary")
    public String getSummary(Model model){
        model.addAttribute("rows", summaryEntityRepository.findAll());
        return "summary";
    }

    @RequestMapping("/clean")
    public String getCleanData(Model model){
        model.addAttribute("rows", cleanDataEntityRepository.findAll());
        return "clean";
    }

    @RequestMapping("/company-jobs")
    public String getJobsPerCompany(Model model){
        model.addAttribute("rows", companyJobsEntityRepository.findAll());
        return "company-jobs";
    }

    @RequestMapping("/popular-jobs")
    public String getPopularJobs(Model model){
        model.addAttribute("rows", jobTitleEntityRepository.findAll());
        return "popular-jobs";
    }

    @RequestMapping("/popular-areas")
    public String getPopularAreas(Model model){
        model.addAttribute("rows", areasEntityRepository.findAll());
        return "popular-areas";
    }

    @RequestMapping("/popular-skills")
    public String getPopularSkills(Model model){
        model.addAttribute("rows", skillsEntityRepository.findAll());
        return "popular-skills";
    }

    @RequestMapping("/jobs-barchart")
    public String getJobsBarChart(Model model) throws IOException {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Popular Job Titles").xAxisTitle("Titles").yAxisTitle("Count").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setXAxisLabelRotation(45);

        List<String> titlesList = new ArrayList<String>();
        final int[] i = {0};
        jobTitleEntityRepository.findAll().forEach(s->{
            if (i[0] < 10) {titlesList.add(s.getAttribute());}
            i[0] += 1;
        });

        List<Long> countList = new ArrayList<Long>();
        final int[] j = {0};
        jobTitleEntityRepository.findAll().forEach(s->{
            if (i[0] < 10) {titlesList.add(s.getCount());}
            i[0] += 1;
        });

        // Series
        chart.addSeries("Job Title Count", titlesList, countList);

        BitmapEncoder.saveBitmap(chart, "src/main/resources/static/popularJobs", BitmapEncoder.BitmapFormat.JPG);


        return "jobs-barchart";
    }


    @RequestMapping("areas-barchart")
    public String getAreasBarChart(Model model) throws IOException {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Popular Areas").xAxisTitle("Areas").yAxisTitle("Count").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setXAxisLabelRotation(45);

        List<String> titlesList = new ArrayList<String>();
        final int[] i = {0};
        areasEntityRepository.findAll().forEach(s->{
            if (i[0] < 10) {titlesList.add(s.getAttribute());}
            i[0] += 1;
        });

        List<Long> countList = new ArrayList<Long>();
        final int[] j = {0};
        areasEntityRepository.findAll().forEach(s->{
            if (i[0] < 10) {titlesList.add(s.getCount());}
            i[0] += 1;
        });

        // Series
        chart.addSeries("Area Count", titlesList, countList);

        BitmapEncoder.saveBitmap(chart, "src/main/resources/static/popularAreas", BitmapEncoder.BitmapFormat.JPG);



        return "areas-barchart";
    }

    @RequestMapping("company-jobs-piechart")
    public String getCompanyJobsPieChart(Model model) throws IOException {

        PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();

        // Customize Chart
        Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
        chart.getStyler().setSeriesColors(sliceColors);




        final int[] i = {0};
        companyJobsEntityRepository.findAll().forEach(s->{
            if(i[0] < 7){chart.addSeries(s.getAttribute(), Long.parseLong(s.getCount()));}
            i[0] += 1;
        });

        BitmapEncoder.saveBitmap(chart, "./src/main/resources/static/jobsForEachCompany", BitmapEncoder.BitmapFormat.JPG);




        return "company-jobs-piechart/index";
    }
}
