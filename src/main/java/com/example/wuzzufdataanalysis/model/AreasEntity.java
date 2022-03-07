package com.example.wuzzufdataanalysis.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AreasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String attribute;
    private String count;

    public AreasEntity(){}

    public AreasEntity(String attribute, String count) {
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

        AreasEntity aggEntity = (AreasEntity) o;

        if (id != null ? !id.equals(aggEntity.id) : aggEntity.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
