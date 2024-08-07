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
    //login and register uses
    User findByEmailAndLoginMethod(String email,LoginMethod loginMethod);
    User findByEmailAndLoginMethodAndIsDeleted(String email, LoginMethod loginMethod, Boolean isDeleted);

    //searching
    List<User> findByEmailContainsAndType(String email,Type type);
    List<User> findByUsernameContainsAndType(String username,Type type);

    //register: check for referral
    Optional<User> findByIdAndIsDeleted(Integer id,Boolean isDeleted);

    List<User> findBySpecificId(String specificId);

    //admin: check accounts that are deleted
    List<User> findByIsDeletedOrderByUpdatedTimeDesc(Boolean isDeleted);
    List<User> findByUsernameContainsAndIsDeletedOrderByUpdatedTimeDesc(String username, Boolean isDeleted);
    List<User> findByEmailContainsAndIsDeletedOrderByUpdatedTimeDesc(String email, Boolean isDeleted);
    List<User> findByIdAndIsDeletedOrderByUpdatedTimeDesc(Integer id, Boolean isDeleted);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User c set c.balance = ?1+c.balance WHERE c.id = ?2",nativeQuery = true)
    Integer updateBalance(Integer balance, Integer id);

}
