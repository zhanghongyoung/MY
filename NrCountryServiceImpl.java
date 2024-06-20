package com.qingdao2world.basic.service.impl;


import com.qingdao2world.basic.api.domain.NrCountry;
import com.qingdao2world.basic.mapper.NrCountryMapper;
import com.qingdao2world.basic.service.INrCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 国家Service业务层处理
 *
 * @author zhy
 * @date 2023-03-23
 */
@Service
public class NrCountryServiceImpl extends BaseService implements INrCountryService {
	@Autowired
	private NrCountryMapper nrCountryMapper;
	/**
	 * 查询国家列表
	 *
	 * @param nrCountry 国家
	 * @return 国家
	 */
	@Override
	public List<NrCountry> selectNrCountryList(NrCountry nrCountry) {
		return nrCountryMapper.selectNrCountryList(nrCountry);
	}

}
