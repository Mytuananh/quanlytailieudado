package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHoSoCT {
    String ctName;
    String maHS;
    String nameHS;
    String loaiHS;
    String namTaoHS;
    String namHoanThanhHS;
    String donViLapHS;
}
