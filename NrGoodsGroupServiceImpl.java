package com.qingdao2world.basic.service.impl;

import com.qingdao2world.basic.api.domain.NrGoodsGroup;
import com.qingdao2world.basic.mapper.NrGoodsGroupMapper;
import com.qingdao2world.basic.service.INrGoodsGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组合商品详情Service业务层处理
 *
 * @author zhy
 * @date 2023-03-24
 */
@Service
@Slf4j
public class NrGoodsGroupServiceImpl extends BaseService implements INrGoodsGroupService {
	@Autowired
	private NrGoodsGroupMapper nrGoodsGroupMapper;


	/**
	 * 查询组合商品详情
	 *
	 * @param id 组合商品详情主键
	 * @return 组合商品详情
	 */
	@Override
	public NrGoodsGroup selectNrGoodsGroupById(Long id) {
		return nrGoodsGroupMapper.selectNrGoodsGroupById(id);
	}

	/**
	 * 查询组合商品详情列表
	 *
	 * @param nrGoodsGroup 组合商品详情
	 * @return 组合商品详情
	 */
	@Override
	public List<NrGoodsGroup> selectNrGoodsGroupList(NrGoodsGroup nrGoodsGroup) {
		return nrGoodsGroupMapper.selectNrGoodsGroupList(nrGoodsGroup);
	}

	/**
	 * 新增组合商品详情
	 *
	 * @param nrGoodsGroup 组合商品详情
	 * @return 结果
	 */
	@Override
	public int insertNrGoodsGroup(NrGoodsGroup nrGoodsGroup) {
		setInsertBaseEntity(nrGoodsGroup);
		return nrGoodsGroupMapper.insertNrGoodsGroup(nrGoodsGroup);
	}


	/**
	 * 修改组合商品详情
	 *
	 * @param nrGoodsGroup 组合商品详情
	 * @return 结果
	 */
	@Override
	public int updateNrGoodsGroup(NrGoodsGroup nrGoodsGroup) {
		setUpdateBaseEntity(nrGoodsGroup);
		return nrGoodsGroupMapper.updateNrGoodsGroup(nrGoodsGroup);
	}

	/**
	 * 批量删除组合商品详情
	 *
	 * @param ids 需要删除的组合商品详情主键
	 * @return 结果
	 */
	@Override
	public int deleteNrGoodsGroupByIds(Long[] ids) {
		return nrGoodsGroupMapper.deleteNrGoodsGroupByIds(ids);
	}

	/**
	 * 删除组合商品详情信息
	 *
	 * @param id 组合商品详情主键
	 * @return 结果
	 */
	@Override
	public int deleteNrGoodsGroupById(Long id) {
		return nrGoodsGroupMapper.deleteNrGoodsGroupById(id);
	}
}
