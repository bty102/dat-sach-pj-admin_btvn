package com.bty.datsachpj.datsachpjadmin_btvn.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hoadon")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill {

    @Id
    @Column(name = "MaHoaDon")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "NgayMua", nullable = false)
    LocalDateTime purchaseDate; // Ngay mua

    @Column(name = "damua")
    Boolean isPaid;// Da mua

    @ManyToOne
    @JoinColumn(name = "makh")
    Customer customer;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<BillDetail> billDetails = new ArrayList<>();
}
