package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sach")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @Column(name = "masach", columnDefinition = "NVARCHAR(50)", length = 50)
    String id;

    @Column(name = "tensach", columnDefinition = "NVARCHAR(50)", length = 50)
    String name;

    @Column(name = "soluong")
    Long quantity; // So luong

    @Column(name = "gia")
    Long price;

    @Column(name = "sotap", columnDefinition = "NVARCHAR(50)", length = 50)
    String volumeNumber; // So tap

    @Column(name = "anh", columnDefinition = "NVARCHAR(50)", length = 50)
    String imagePath;

    @Column(name = "NgayNhap")
    LocalDateTime importDate; // Ngay nhap

    @Column(name = "tacgia", columnDefinition = "NVARCHAR(50)", length = 50)
    String author; // Tac gia

    @ManyToOne
    @JoinColumn(name = "maloai")
    Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<BillDetail> billDetails = new ArrayList<>();

}
