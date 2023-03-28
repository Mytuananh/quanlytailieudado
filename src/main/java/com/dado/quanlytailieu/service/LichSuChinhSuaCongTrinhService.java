package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.entity.LichSuChinhSuaCongTrinh;
import com.dado.quanlytailieu.repository.LichSuChinhSuaCongTrinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuChinhSuaCongTrinhService {
    @Autowired
    LichSuChinhSuaCongTrinhRepository lichSuChinhSuaCongTrinhRepository;

    public List<LichSuChinhSuaCongTrinh> getAllLichSuChinhSuaCongTrinh(String maCT) {
        return lichSuChinhSuaCongTrinhRepository.findAllByMaCT(maCT);
    }
}
