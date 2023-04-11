package com.dado.quanlytailieu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CongTrinhDTO {
    String maCT;
    String name;
    Integer hoSoCTCount;
    Integer hoSoViPhamCount;
}
