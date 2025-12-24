package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ChiTietHoaDon")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillDetail {

    @Id
    @Column(name = "MaChiTietHD")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "SoLuongMua", nullable = false)
    Integer quantity; // So luong sach mua trong hoa don

    @Column(name = "DaMua")
    Boolean isPaid; // Da mua sach hay chua

    @ManyToOne
    @JoinColumn(name = "MaHoaDon")
    Bill bill;

    @ManyToOne
    @JoinColumn(name = "MaSach")
    Book book;

    // lay thanh tien
    public long getTotal() {
        return book.getPrice() * quantity;
    }
}
