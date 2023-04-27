package com.dado.quanlytailieu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoSoCTDTO {
    String ctName;
    String maHS;
    String nameHS;
    String loaiHS;
    String namTaoHS;
    String namHoanThanhHS;
    String donViLapHS;
}
