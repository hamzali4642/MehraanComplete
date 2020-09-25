package com.demit.mehraan.Model;

public class RequestModel {
    String name;
    Integer id;

    public RequestModel(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    public RequestModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}