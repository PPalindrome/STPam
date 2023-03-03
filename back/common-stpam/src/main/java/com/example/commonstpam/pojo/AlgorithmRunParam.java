package com.example.commonstpam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmRunParam {

    private String modelName;

    private String params;

    private String filePath;
}
