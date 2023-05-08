package com.employmentBackend.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Job {

    @Id
    @GeneratedValue
    private Long id;
    private String position;
    private String description;
    private int Offer = -1;
    private Long qualifications= -1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOffer() {
        return Offer;
    }

    public void setOffer(int offer) {
        Offer = offer;
    }

    public Long getQualifications() {
        return qualifications;
    }

    public void setQualifications(Long qualifications) {
        this.qualifications = qualifications;
    }
}
