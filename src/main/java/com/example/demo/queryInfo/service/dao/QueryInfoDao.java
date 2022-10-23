package com.example.demo.queryInfo.service.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryInfoDao {
    String queryRoleName(@Param("id") String id);
}
