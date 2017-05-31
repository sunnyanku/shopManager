package com.bebo.shopmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.bebo.shopmanager.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    List<Manager> findByLastName(String lastName);
    Manager findByUsername(String username);
}

