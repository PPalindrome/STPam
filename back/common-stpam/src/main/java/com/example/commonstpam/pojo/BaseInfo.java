package com.example.commonstpam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfo {

    @Value("${server.port}")
    private String port;

    private static final String ip="http://localhost";

}
