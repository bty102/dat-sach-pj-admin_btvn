package com.bty.datsachpj.datsachpjadmin_btvn.repository;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(String name, String author, Pageable pageable);

    Page<Book> findByCategory_Id(String categoryId, Pageable pageable);

    @Query("""
        SELECT b
        FROM Book b
        LEFT JOIN b.billDetails bd
        GROUP BY b
        ORDER BY COUNT(bd) DESC
    """)
    Page<Book> findTopBooksByBillDetailCount(Pageable pageable);
}
