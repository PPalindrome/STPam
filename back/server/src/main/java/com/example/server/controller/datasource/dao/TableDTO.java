package com.example.server.controller.datasource.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private String name;
    private String size;
    private String date;
}
