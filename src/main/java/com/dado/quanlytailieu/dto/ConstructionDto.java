package com.dado.quanlytailieu.dto;

import java.util.List;

import com.dado.quanlytailieu.enums.ConstructionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionDto {
    Long id;
    String name;
    String code;
    String location;
    ConstructionType type;
    String size;
    String address;
    List<Resource> images;
    List<ConstructionDocumentDto> docs;
}
