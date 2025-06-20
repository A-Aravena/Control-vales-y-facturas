package com.transporte.cl.repository;

import com.transporte.cl.models.DAO.User.SelectUserDAO;
import com.transporte.cl.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query(value = "select id, nombres from users order by id asc", nativeQuery = true)
  List<Object[]> selectUser();

  @Query(value = "select u.id, u.email, u.nombres, u.notificacion, u.username from users u order by u.id asc", nativeQuery = true)
  List<Object[]> getUsersFull();

  @Query(value = "select email from users where notificacion = true", nativeQuery = true)
  List<String> notificacion();



}
