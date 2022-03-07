package com.example.wuzzufdataanalysis.bootStrapData;

import com.example.wuzzufdataanalysis.model.RowEntity;
import com.example.wuzzufdataanalysis.model.SummaryEntity;
import com.example.wuzzufdataanalysis.model.cleanDataEntity;
import com.example.wuzzufdataanalysis.repositories.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BootStrapData implements CommandLineRunner {

    private final RowEntityRepository rowEntityRepository;
    private final SummaryEntityRepository summaryEntityRepository;
    private final CompanyJobsEntityRepository CompanyJobsEntityRepository;
    private final cleanDataEntityRepository cleanDataEntityRepository;
    private final CompanyJobsEntityRepository companyJobsEntityRepository;
    private final JobTitleEntityRepository jobTitleEntityRepository;
    private final SkillsEntityRepository skillsEntityRepository;


    public BootStrapData(RowEntityRepository rowEntityRepository, SummaryEntityRepository summaryEntityRepository, CompanyJobsEntityRepository aggEntityRepository, cleanDataEntityRepository cleanDataEntityRepositor, com.example.wuzzufdataanalysis.repositories.CompanyJobsEntityRepository companyJobsEntityRepository, JobTitleEntityRepository jobTitleEntityRepository, SkillsEntityRepository skillsEntityRepository) {
        this.rowEntityRepository = rowEntityRepository;
        this.summaryEntityRepository = summaryEntityRepository;
        this.CompanyJobsEntityRepository = aggEntityRepository;
        this.cleanDataEntityRepository = cleanDataEntityRepositor;
        this.companyJobsEntityRepository = companyJobsEntityRepository;
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

    }
}
