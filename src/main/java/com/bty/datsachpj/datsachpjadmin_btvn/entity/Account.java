package com.bty.datsachpj.datsachpjadmin_btvn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "DangNhap")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    @Id
    @Column(name = "TenDangNhap", columnDefinition = "NVARCHAR(50)", length = 50)
    String username;

    @Column(name = "MatKhau", columnDefinition = "NVARCHAR(1000)", length = 1000, nullable = false)
    String password;

    @Column(name = "Quyen", nullable = false)
    boolean authority;
}
