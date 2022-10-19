package com.example.demo.queryInfo.service.Impl;

import com.example.demo.queryInfo.service.QueryInfoService;
import com.example.demo.response.ResponseBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("QueryInfoService")
public class QueryInfoServiceImpl implements QueryInfoService {
    @Override
    public Map<String, Object> queryInfo(Map<String, Object> params) {
        List list = new ArrayList<>();
        list.add(1);
        return ResponseBean.success(list);
    }
}
