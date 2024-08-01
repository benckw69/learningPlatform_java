package com.benckw69.learningPlatform_java.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select * from users where email = ?1 && type = ?2 && login_method = 'email'",nativeQuery = true)
    User findEmailExist(String email, String type);

    //List<Users> findUsersByEmail(String email);

    Optional<User> findById(Integer id);

    List<User> findBySpecificId(String specificId);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users c set c.balance = c.balance+?1 WHERE c.id = ?2",nativeQuery = true)
    Integer updateBalance(Integer balance, Integer id);




}
