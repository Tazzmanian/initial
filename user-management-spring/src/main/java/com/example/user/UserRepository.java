package com.example.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserName(String userName);

    public User findByUserId(Long id);

    public Page<User> findByFirstNameLike(String firstName, Pageable pageRequest);

    public Page<User> findByEnabledTrue(Pageable pageRequest);

    public Page<User> findByEnabledFalse(Pageable pageRequest);
}
