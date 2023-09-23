package com.example.server.controller.dataset.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetDTO {
    private String name;
    private String path;
    private String date;
    private String description;
}
