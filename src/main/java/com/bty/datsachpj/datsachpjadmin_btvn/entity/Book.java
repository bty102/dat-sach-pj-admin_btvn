package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotNull(message = "Mã sách không được để trống")
    @Size(min = 1, max = 50, message = "Mã sách phải có ít nhất 1 và tối đa 50 ký tự")
    String id;

    @Column(name = "tensach", columnDefinition = "NVARCHAR(50)", length = 50)
    @Size(min = 1, max = 50, message = "Tên sách phải có ít nhất 1 và tối đa 50 ký tự")
    String name;

    @Column(name = "soluong")
//    @Length(min = 0, message = "Số lượng ít nhất là 0")
    @Min(value = 0, message = "Số lượng ít nhất là 0")
    Long quantity; // So luong

    @Column(name = "gia")
//    @Length(min = 0, message = "Giá ít nhất là 0")
    @Min(value = 0, message = "Giá ít nhất là 0")
    Long price; // Dong

    @Column(name = "sotap", columnDefinition = "NVARCHAR(50)", length = 50)
    @Size(min = 1, max = 50, message = "Số tập phải có ít nhất 1 và tối đa 50 ký tự")
    String numberOfVolumes; // So luong tap

    @Column(name = "anh", columnDefinition = "NVARCHAR(500)", length = 500)
//    @Size(min = 1, max = 500, message = "Đường dẫn ảnh phải có ít nhất 1 và tối đa 500 ký tự")
    String imagePath;

    @Column(name = "NgayNhap")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime importDate; // Ngay nhap

    @Column(name = "tacgia", columnDefinition = "NVARCHAR(50)", length = 50)
    @Size(min = 1, max = 50, message = "Tác giả phải có ít nhất 1 và tối đa 50 ký tự")
    String author; // Tac gia

    @ManyToOne
    @JoinColumn(name = "maloai")
    Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<BillDetail> billDetails = new ArrayList<>();

    public long getNumberOfBillDetails() {
        return billDetails.size();
    }

}
