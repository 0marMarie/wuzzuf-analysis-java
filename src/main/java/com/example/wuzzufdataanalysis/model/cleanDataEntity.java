package com.example.wuzzufdataanalysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class cleanDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String summary;
    private String title;
    private String company;
    private String location;
    private String type;
    private String level;
    private String yearsExp;
    private String country;
    private String skills;

    public cleanDataEntity(){}

    public cleanDataEntity(String summary, String title, String company, String location, String type, String level, String yearsExp, String country, String skills) {
        this.summary = summary;
        this.title = title;
        this.company = company;
        this.location = location;
        this.type = type;
        this.level = level;
        this.yearsExp = yearsExp;
        this.country = country;
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "SummaryEntity{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", yearsExp='" + yearsExp + '\'' +
                ", country='" + country + '\'' +
                ", skills='" + skills + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        cleanDataEntity that = (cleanDataEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getYearsExp() {
        return yearsExp;
    }

    public void setYearsExp(String yearsExp) {
        this.yearsExp = yearsExp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
