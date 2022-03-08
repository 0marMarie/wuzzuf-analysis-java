package com.example.wuzzufdataanalysis.bootStrapData;

import com.example.wuzzufdataanalysis.model.*;
import com.example.wuzzufdataanalysis.repositories.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.count;


@Component
public class BootStrapData implements CommandLineRunner {

    private final RowEntityRepository rowEntityRepository;
    private final SummaryEntityRepository summaryEntityRepository;
    private final CompanyJobsEntityRepository CompanyJobsEntityRepository;
    private final cleanDataEntityRepository cleanDataEntityRepository;
    private final AreasEntityRepository areasEntityRepository;
    private final JobTitleEntityRepository jobTitleEntityRepository;
    private final SkillsEntityRepository skillsEntityRepository;


    public BootStrapData(RowEntityRepository rowEntityRepository, SummaryEntityRepository summaryEntityRepository, CompanyJobsEntityRepository aggEntityRepository, cleanDataEntityRepository cleanDataEntityRepositor, com.example.wuzzufdataanalysis.repositories.CompanyJobsEntityRepository companyJobsEntityRepository, AreasEntityRepository areasEntityRepository, JobTitleEntityRepository jobTitleEntityRepository, SkillsEntityRepository skillsEntityRepository) {
        this.rowEntityRepository = rowEntityRepository;
        this.summaryEntityRepository = summaryEntityRepository;
        this.CompanyJobsEntityRepository = aggEntityRepository;
        this.cleanDataEntityRepository = cleanDataEntityRepositor;
        this.areasEntityRepository = areasEntityRepository;
        this.jobTitleEntityRepository = jobTitleEntityRepository;
        this.skillsEntityRepository = skillsEntityRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        SparkSession sparkSession = SparkSession
                .builder()
                .master("local[2]")
                .appName("Integrating Spring-boot with Apache Spark")
                .getOrCreate();
        Dataset<Row> dataSet = sparkSession.read().option("header", true).csv("src/main/resources/Wuzzuf_Jobs.csv");
        dataSet.toLocalIterator().forEachRemaining(s->{
            RowEntity rowEntity = new RowEntity(
                    s.getString(0),
                    s.getString(1),
                    s.getString(2),
                    s.getString(3),
                    s.getString(4),
                    s.getString(5),
                    s.getString(6),
                    s.getString(7)
            );

            rowEntityRepository.save(rowEntity);

        });

        Dataset<Row> dataSummary = dataSet.summary();
        dataSummary.toLocalIterator().forEachRemaining(s->{
            SummaryEntity summary = new SummaryEntity(
                    s.getString(0),
                    s.getString(1),
                    s.getString(2),
                    s.getString(3),
                    s.getString(4),
                    s.getString(5),
                    s.getString(6),
                    s.getString(7),
                    s.getString(8)
            );
            summaryEntityRepository.save(summary);
        });

        Dataset<Row> cleanData = dataSet.dropDuplicates().summary();
        cleanData.toLocalIterator().forEachRemaining(s->{
            cleanDataEntity cleanDataEntity = new cleanDataEntity(
                    s.getString(0),
                    s.getString(1),
                    s.getString(2),
                    s.getString(3),
                    s.getString(4),
                    s.getString(5),
                    s.getString(6),
                    s.getString(7),
                    s.getString(8)
            );
            cleanDataEntityRepository.save(cleanDataEntity);
        });

        Dataset<Row> jobsPerCompany = dataSet.groupBy("Company").agg(count("Title")).orderBy(col("count(Title)").desc());
        jobsPerCompany.toLocalIterator().forEachRemaining(s->{
            CompanyJobsEntity companyJobsEntity = new CompanyJobsEntity(
                    s.getString(0),
                    String.valueOf(s.getLong(1))
            );

            CompanyJobsEntityRepository.save(companyJobsEntity);
        });

        Dataset<Row> popularJobs = dataSet.groupBy("Title").agg(count("Title")).orderBy(col("count(Title)").desc());
        popularJobs.toLocalIterator().forEachRemaining(s->{
            JobTitleEntity jobTitleEntity = new JobTitleEntity(
                    s.getString(0),
                    String.valueOf(s.getLong(1))
            );

            jobTitleEntityRepository.save(jobTitleEntity);
        });

        Dataset<Row> popularAreas = dataSet.groupBy("Location").agg(count("Location")).orderBy(col("count(Location)").desc());
        popularAreas.toLocalIterator().forEachRemaining(s->{
            AreasEntity areasEntity = new AreasEntity(
                    s.getString(0),
                    String.valueOf(s.getLong(1))
            );

            areasEntityRepository.save(areasEntity);
        });

        List<String> allSkills = new ArrayList<String>();
        dataSet.select("Skills").toLocalIterator().forEachRemaining(s -> {
            List<String> skills = Arrays.asList(s.getString(0).split(","));
            allSkills.addAll(skills);
        });
        Dataset<Row> Allskills = sparkSession.createDataset(allSkills, Encoders. STRING()).toDF("Skill");
        Allskills = Allskills.groupBy("Skill").agg(count("Skill")).orderBy(col("count(Skill)").desc());
        Allskills.toLocalIterator().forEachRemaining(s->{
            SkillsEntity skillsEntity = new SkillsEntity(
                    s.getString(0),
                    String.valueOf(s.getLong(1))
            );

            skillsEntityRepository.save(skillsEntity);
        });

    }
}
