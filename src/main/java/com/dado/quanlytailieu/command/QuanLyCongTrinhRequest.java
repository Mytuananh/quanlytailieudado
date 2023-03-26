package com.dado.quanlytailieu.command;

import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.entity.Image;
import com.dado.quanlytailieu.enums.CongTrinhType;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class QuanLyCongTrinhRequest {
    private String maCT;
    private String name;
    private String viTri;
    private CongTrinhType type;
    private String quyMo;
    private String thietBi;
    private List<String> congTrinhLienQuan; // tim theo maCT
    String thongTinKhac;
    List<Image> images;
    List<FileEntity> files;
}
