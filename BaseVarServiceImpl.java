package com.qingdao2world.basic.service.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.qingdao2world.basic.api.domain.SpBaseVar;
import com.qingdao2world.basic.mapper.SpBaseVarMapper;
import com.qingdao2world.basic.service.BaseVarService;
import com.qingdao2world.common.core.utils.redis.RedisKeyStore;
import com.qingdao2world.common.core.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class BaseVarServiceImpl implements BaseVarService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SpBaseVarMapper spBaseVarMapper;

    private RedisUtil getRedisUtil() {
        return new RedisUtil(jedisPool);
    }


    @Override
    public String getServiceVar(String serviceName) {
        return getVar("ozshop.service." + serviceName);
    }

    protected Double getServiceVarDouble(String serviceName) {
        return Convert.toDouble(getServiceVar(serviceName));
    }

    @Override
    public String getPortalVar(String serviceName) {
        return getVar("ozshop.portal." + serviceName);
    }

    @Override
    public String getCommonVar(String serviceName) {
        return getVar("ozshop.common." + serviceName);
    }

    @Override
    public String getSellerVar(String serviceName) {
        return getVar("ozshop.seller." + serviceName);
    }

    public String getVar(String name) {
        RedisUtil redisUtil = getRedisUtil();
        String value = redisUtil.getMapByField(RedisKeyStore.SHOP_BASEVAR, name, RedisUtil.MAX_DEFAULT_MINUTE);
        if (StrUtil.isEmpty(value)) {
            Map<String, String> varMap = initialize(false);
            value = varMap.get(name);
        }
        return value;
    }

    public Map<String, String> initialize(boolean reload) {
        RedisUtil redisUtil = getRedisUtil();
        String key = RedisKeyStore.SHOP_BASEVAR;
        Map<String, String> varMap = redisUtil.getMap(key, 0);
        if (varMap == null || varMap.size() <= 0) {
            try {
                putDbVar(varMap);
                redisUtil.setMap(key, varMap, RedisUtil.MAX_DEFAULT_MINUTE);
            } catch (Exception e){
                log.error(e.getMessage());
            }

        }
        if (reload) {
            redisUtil.del(RedisKeyStore.SHOP_BASEVAR);
            // 删除等级权益信息
            redisUtil.delPreKey(RedisKeyStore.LIST_USER_RIGHTS);
            // 删除会员等级信息
            redisUtil.delPreKey(RedisKeyStore.OBJECT_USER_LEVEL);
            redisUtil.delPreKey(RedisKeyStore.OBJECT_STORE);
            redisUtil.delPreKey(RedisKeyStore.OBJECT_SYSTORESHOP);
            redisUtil.delPreKey(RedisKeyStore.OBJECT_APP);
            redisUtil.delPreKey(RedisKeyStore.OBJECT_WAREHOUSE);
            redisUtil.delPreKey(RedisKeyStore.SHOP_INDEX);
        }
        return varMap;
    }


    private void putDbVar(Map<String, String> resultMap) {
        HashMap<String, SpBaseVar> map = new HashMap<String, SpBaseVar>();
        List<Dict> baseVar = spBaseVarMapper.getList();
        if (baseVar != null && !baseVar.isEmpty()) {
            for (Dict dt : baseVar) {
                String id = dt.getStr("var_id");
                SpBaseVar vd = new SpBaseVar(dt.getStr("var_name"), dt.getStr("var_value"), dt.getStr("parent_id"),
                        dt.getInt("is_var"));
                map.put(id, vd);
            }

            Set<Map.Entry<String, SpBaseVar>> es = map.entrySet();
            for (Map.Entry<String, SpBaseVar> e : es) {
                String key = e.getKey();
                SpBaseVar value = e.getValue();
                String name = getNameFromId(map, key);
                resultMap.put(name, value.getVarValue());
            }
        }
    }

    private static String getNameFromId(Map<String, SpBaseVar> map, String varId) {
        String s = "";
        String id = varId;
        SpBaseVar d;
        while ((d = map.get(id)) != null) {
            if (!s.isEmpty())
                s = "." + s;
            s = d.getVarName() + s;
            id = d.getParentId();
        }
        return s;
    }
}
