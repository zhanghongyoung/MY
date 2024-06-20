package com.qingdao2world.basic.service.impl;

import com.qingdao2world.basic.api.domain.NrCurrency;
import com.qingdao2world.basic.mapper.NrCurrencyMapper;
import com.qingdao2world.basic.service.INrCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 币种Service业务层处理
 *
 * @author CHEN HUI
 * @date 2023-03-24
 */
@Service
public class NrCurrencyServiceImpl extends BaseService implements INrCurrencyService {
    @Autowired
    private NrCurrencyMapper nrCurrencyMapper;


    /**================================自定义接口================================**/

    /**
     * 下拉选择专用接口
     *
     * @return
     */
    @Override
    public List<NrCurrency> select4Select() {
        return nrCurrencyMapper.select4Select();
    }
}
