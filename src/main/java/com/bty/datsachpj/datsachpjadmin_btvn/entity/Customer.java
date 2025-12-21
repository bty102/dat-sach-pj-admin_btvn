package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KhachHang")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @Column(name = "makh")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "hoten", columnDefinition = "NVARCHAR(50)", length = 50)
    String fullName;

    @Column(name = "diachi", columnDefinition = "NVARCHAR(50)", length = 50)
    String address;

    @Column(name = "sodt", columnDefinition = "NVARCHAR(50)", length = 50)
    String phoneNumber;

    @Column(name = "email", columnDefinition = "NVARCHAR(50)", length = 50)
    String email;

    @Column(name = "tendn", columnDefinition = "NVARCHAR(50)", length = 50)
    String username;

    @Column(name = "pass", columnDefinition = "NVARCHAR(50)", length = 50)
    String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Bill> bills = new ArrayList<>();
}
