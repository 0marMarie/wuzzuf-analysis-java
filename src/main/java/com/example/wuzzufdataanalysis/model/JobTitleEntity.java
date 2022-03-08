package com.example.wuzzufdataanalysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class JobTitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String attribute;
    private String count;

    public JobTitleEntity(){}

    public JobTitleEntity(String attribute, String count) {
        this.attribute = attribute;
        this.count = count;
    }

    @Override
    public String toString() {
        return "AggEntity{" +
                "id=" + id +
                ", attribute='" + attribute + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobTitleEntity aggEntity = (JobTitleEntity) o;

        if (id != null ? !id.equals(aggEntity.id) : aggEntity.id != null) return false;

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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
