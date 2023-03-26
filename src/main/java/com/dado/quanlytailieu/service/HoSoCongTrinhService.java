package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HoSoCongTrinhService {
    @Autowired
    HoSoCongTrinhRepository hoSoCongTrinhRepository;

    @Autowired
    CongTrinhRepository congTrinhRepository;

    @Autowired
    FileService fileService;

    @Autowired
    FileRepository fileRepository;

    @Value("${extern.resources.path.file}")
    private String pathFile;

}
