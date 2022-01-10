package com.vpinfotech.aws.dao;

import com.vpinfotech.aws.entity.ImageStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<ImageStorage,Integer> {

}
