package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loai")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @Column(name = "maloai", columnDefinition = "NVARCHAR(50)", length = 50)
    String id;

    @Column(name = "tenloai", columnDefinition = "NVARCHAR(50)", length = 50)
    String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Book> books = new ArrayList<>();
}
