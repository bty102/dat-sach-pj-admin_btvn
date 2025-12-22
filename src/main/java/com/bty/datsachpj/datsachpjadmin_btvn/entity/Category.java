package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 50, message = "Mã loại phải có ít nhất là 1 và nhiều nhất là 50 ký tự")
    @NotNull(message = "Mã loại không được để trống")
    String id;

    @Column(name = "tenloai", columnDefinition = "NVARCHAR(50)", length = 50)
    @Size(min = 1, max = 50, message = "Tên loại phải có ít nhất là 1 và nhiều nhất là 50 ký tự")
    String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Book> books = new ArrayList<>();
}
