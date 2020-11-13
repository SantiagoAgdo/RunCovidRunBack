package com.runcovidrunback.runcovidrun.repository;

import com.runcovidrunback.runcovidrun.entity.GetData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetDataRepository extends JpaRepository<GetData, Integer> {


}
