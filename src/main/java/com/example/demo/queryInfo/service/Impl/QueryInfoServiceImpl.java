package com.example.demo.queryInfo.service.Impl;

import com.example.demo.queryInfo.service.QueryInfoService;
import com.example.demo.queryInfo.service.dao.QueryInfoDao;
import com.example.demo.response.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("QueryInfoService")
public class QueryInfoServiceImpl implements QueryInfoService {
    @Autowired
    QueryInfoDao queryInfoDao;

    @Override
    public Map<String, Object> queryInfo(Map<String, Object> params) {
//        String id = "1";
        String id = (String) params.get("id");
        String roleName = queryInfoDao.queryRoleName(id);
        return ResponseBean.success(roleName);
    }
}
