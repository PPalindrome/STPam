package com.example.server.service;

import java.util.List;
import java.util.Map;

public interface IDirectoryStructureService {
    List<String> getFileDirectoryStructure(String dirPath ,int flag);
}
