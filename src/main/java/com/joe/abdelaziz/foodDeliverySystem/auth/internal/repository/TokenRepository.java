package com.joe.abdelaziz.foodDeliverySystem.auth.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.auth.internal.entity.Token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query(
      """
      select t from Token t
      where t.userId = :id and (t.expired = false and t.revoked = false)
      """)
  List<Token> findAllValidTokenByUser(Long id);

  @Query(
      """
      select t from Token t
      where t.courierId = :id and (t.expired = false and t.revoked = false)
      """)
  List<Token> findAllValidTokenByCourier(Long id);

  Optional<Token> findByToken(String token);
}









