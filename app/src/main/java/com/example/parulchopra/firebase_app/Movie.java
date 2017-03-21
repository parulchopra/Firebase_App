package com.example.parulchopra.firebase_app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by parul chopra on 21-03-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    String name;
    String description;

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

