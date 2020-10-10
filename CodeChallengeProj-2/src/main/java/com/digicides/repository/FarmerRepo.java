package com.digicides.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digicides.entity.Farmer;

@Repository
public interface FarmerRepo extends JpaRepository<Farmer, Long> {

}
