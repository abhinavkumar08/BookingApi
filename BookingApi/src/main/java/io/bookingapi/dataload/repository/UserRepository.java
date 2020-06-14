package io.bookingapi.dataload.repository;

import org.springframework.data.repository.CrudRepository;

import io.bookingapi.dataload.entity.UserDetail;

public interface UserRepository extends CrudRepository<UserDetail, String> {

}
