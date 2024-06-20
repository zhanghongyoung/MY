package com.qingdao2world.basic.service.impl;

import com.qingdao2world.basic.api.domain.NrGoodsClass;
import com.qingdao2world.basic.mapper.NrGoodsClassMapper;
import com.qingdao2world.basic.service.INrGoodsClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类别Service业务层处理
 *
 * @author zhy
 * @date 2023-03-23
 */
@Service
public class NrGoodsClassServiceImpl extends BaseService implements INrGoodsClassService {
	@Autowired
	private NrGoodsClassMapper nrGoodsClassMapper;

	/**
	 * 查询商品类别列表
	 *
	 * @param nrGoodsClass 商品类别
	 * @return 商品类别
	 */
	@Override
	public List<NrGoodsClass> selectNrGoodsClassList(NrGoodsClass nrGoodsClass) {
		return nrGoodsClassMapper.selectNrGoodsClassList(nrGoodsClass);
	}

}
