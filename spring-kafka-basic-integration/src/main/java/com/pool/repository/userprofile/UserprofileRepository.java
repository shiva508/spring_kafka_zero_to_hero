package com.pool.repository.userprofile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pool.modal.Userprofile;

@Repository
public interface UserprofileRepository extends JpaRepository<Userprofile, Integer> {

}
