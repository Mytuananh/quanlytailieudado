package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.extra.MapStringStringJsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LichSuHoSoCongTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "construction_document_id", referencedColumnName = "id")
    HoSoCongTrinh hoSoCongTrinh;

    @Type(MapStringStringJsonType.class)
    @Column(columnDefinition = "jsonb")
    Map<String, String> editingInfo;    // luu thong tin truoc khi chinh sua

    String editUser;

    LocalDateTime editTime;
}
