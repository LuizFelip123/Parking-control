package com.api.Parkingcontrol.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.Parkingcontrol.models.UserModel;

@Repository
public interface UserRepository  extends CrudRepository<UserModel,Long>{
    Optional<UserModel> findByUserName(String userName);
}
