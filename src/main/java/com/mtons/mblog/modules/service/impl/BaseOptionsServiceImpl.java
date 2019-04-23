package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.entity.Options;
import com.mtons.mblog.modules.mapper.BaseOptionsMapper;
import com.mtons.mblog.modules.service.BaseOptionsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseOptionsServiceImpl  implements BaseOptionsService {

    @Autowired
    BaseOptionsMapper baseOptionsMapper;



    @Override
    public List<Options> findAll() {
        List<Options> list = baseOptionsMapper.findAll();
        List<Options> rets = new ArrayList<>();
        for (Options po : list) {
            Options r = new Options();
            BeanUtils.copyProperties(po, r);
            rets.add(r);
        }
        return rets;
    }

    @Override
    @Transactional
    public void update(Map<String, String> options) {
        if (options == null) {
            return;
        }

        options.forEach((key, value) -> {
            Options entity = baseOptionsMapper.findByKey(key);
            String val = StringUtils.trim(value);
            if (entity != null) {
                entity.setValue(val);
            } else {
                entity = new Options();
                entity.setKey(key);
                entity.setValue(val);
            }
            baseOptionsMapper.save(entity);
        });
    }

    @Override
    public void initSettings(Resource resource) {
//        Session session = entityManager.unwrap(Session.class);
//        session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
    }
}
