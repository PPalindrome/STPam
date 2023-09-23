package com.example.server.controller.datasource.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadDTO {
    private String databaseName;
    private String tableName;

}
