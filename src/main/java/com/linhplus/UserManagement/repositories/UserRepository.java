package com.linhplus.UserManagement.repositories;

import com.linhplus.UserManagement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    Long deleteByUsername(String username);
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id in :ids")
    void deleteUsersByIds(@Param(value = "ids") List<Long> ids);

    @Query("SELECT u FROM User u WHERE u.id in :ids")
    List<User> findUsersByIds(@Param(value = "ids") List<Long> ids);

    @Query("SELECT u.id FROM User u WHERE u.id in :ids")
    List<Long> findExistedIds(List<Long> ids);

}