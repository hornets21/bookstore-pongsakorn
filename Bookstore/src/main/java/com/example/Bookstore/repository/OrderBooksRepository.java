package com.example.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Bookstore.entity.OrderBooks;

@Repository
public interface OrderBooksRepository extends JpaRepository<OrderBooks, Long> {

	@Query(nativeQuery = true, value = "SELECT odb.book_id FROM order_books odb where 1=1 and odb.user_id = :userId")
	public int[] findOrderByUserId(Long userId);
	
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM order_books where user_id = :userId")
	public void deleteOrderBookByUserId(Long userId);
}
