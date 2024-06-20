package com.qingdao2world.basic.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.qingdao2world.basic.api.domain.NrGoodsBrand;
import com.qingdao2world.basic.mapper.NrGoodsBrandMapper;
import com.qingdao2world.basic.service.INrGoodsBrandService;
import com.qingdao2world.common.core.constant.UserConstants;
import com.qingdao2world.common.core.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌Service业务层处理
 *
 * @author zhy
 * @date 2023-03-23
 */
@Service
public class NrGoodsBrandServiceImpl extends BaseService implements INrGoodsBrandService {
	@Autowired
	private NrGoodsBrandMapper nrGoodsBrandMapper;

	/**
	 * 查询品牌列表
	 *
	 * @param nrGoodsBrand 品牌
	 * @return 品牌
	 */
	@Override
	public List<NrGoodsBrand> selectNrGoodsBrandList(NrGoodsBrand nrGoodsBrand) {
		return nrGoodsBrandMapper.selectNrGoodsBrandList(nrGoodsBrand);
	}

	/**
	 * 查询品牌
	 *
	 * @param id 品牌主键
	 * @return 品牌
	 */
	@Override
	public NrGoodsBrand selectNrGoodsBrandById(Long id) {
		return nrGoodsBrandMapper.selectNrGoodsBrandById(id);
	}

	/**
	 * 新增品牌
	 *
	 * @param nrGoodsBrand 品牌
	 * @return 结果
	 */
	@Override
	public int insertNrGoodsBrand(NrGoodsBrand nrGoodsBrand) {
		setInsertBaseEntity(nrGoodsBrand);
		return nrGoodsBrandMapper.insertNrGoodsBrand(nrGoodsBrand);
	}

	/**
	 * 修改品牌
	 *
	 * @param nrGoodsBrand 品牌
	 * @return 结果
	 */
	@Override
	public int updateNrGoodsBrand(NrGoodsBrand nrGoodsBrand) {
		setUpdateBaseEntity(nrGoodsBrand);
		return nrGoodsBrandMapper.updateNrGoodsBrand(nrGoodsBrand);
	}

	/**
	 * 批量删除品牌
	 *
	 * @param ids 需要删除的品牌主键
	 * @return 结果
	 */
	@Override
	public int deleteNrGoodsBrandByIds(Long[] ids) {
		return nrGoodsBrandMapper.deleteNrGoodsBrandByIds(ids);
	}

	@Override
	public String checkBrandNameUnique(NrGoodsBrand nrGoodsBrand) {
		Long menuId = StringUtil.isNull(nrGoodsBrand.getId()) ? -1L : nrGoodsBrand.getId();
		NrGoodsBrand info = nrGoodsBrandMapper.checkBrandNameUnique(nrGoodsBrand.getName());
		if (ObjectUtil.isNotNull(info) && info.getId().longValue() != menuId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

}
