package com.qingdao2world.basic.service.impl;

import com.qingdao2world.basic.api.domain.NrGoodsPhoto;
import com.qingdao2world.basic.mapper.NrGoodsPhotoMapper;
import com.qingdao2world.basic.service.INrGoodsPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片上传Service业务层处理
 *
 * @author xhabs
 * @date 2023-04-04
 */
@Service
public class NrGoodsPhotoServiceImpl extends BaseService implements INrGoodsPhotoService {
	@Autowired
	private NrGoodsPhotoMapper nrGoodsPhotoMapper;


	/**
	 * 查询图片上传
	 *
	 * @param goodsId 图片上传主键
	 * @return 图片上传
	 */
	@Override
	public List<NrGoodsPhoto> selectNrGoodsPhotoByGoodsId(Long goodsId) {
		return nrGoodsPhotoMapper.selectNrGoodsPhotoByGoodsId(goodsId);
	}

	/**
	 * 查询图片上传列表
	 *
	 * @param nrGoodsPhoto 图片上传
	 * @return 图片上传
	 */
	@Override
	public List<NrGoodsPhoto> selectNrGoodsPhotoList(NrGoodsPhoto nrGoodsPhoto) {
		return nrGoodsPhotoMapper.selectNrGoodsPhotoList(nrGoodsPhoto);
	}

	/**
	 * 新增图片上传
	 *
	 * @param nrGoodsPhoto 图片上传
	 * @return 结果
	 */
	@Override
	public int insertNrGoodsPhoto(NrGoodsPhoto nrGoodsPhoto) {
        return nrGoodsPhotoMapper.insertNrGoodsPhoto(nrGoodsPhoto);
	}

	/**
	 * 修改图片上传
	 *
	 * @param nrGoodsPhoto 图片上传
	 * @return 结果
	 */
	@Override
	public int updateNrGoodsPhoto(NrGoodsPhoto nrGoodsPhoto) {
		setUpdateBaseEntity(nrGoodsPhoto);
		return nrGoodsPhotoMapper.updateNrGoodsPhoto(nrGoodsPhoto);
	}

	/**
	 * 批量删除图片上传
	 *
	 * @param goodsIds 需要删除的图片上传主键
	 * @return 结果
	 */
	@Override
	public int deleteNrGoodsPhotoByGoodsIds(Long[] goodsIds) {
		return nrGoodsPhotoMapper.deleteNrGoodsPhotoByGoodsIds(goodsIds);
	}

	/**
	 * 删除图片上传信息
	 *
	 * @param goodsId 图片上传主键
	 * @return 结果
	 */
	@Override
	public int deleteNrGoodsPhotoByGoodsId(Long goodsId) {
		return nrGoodsPhotoMapper.deleteNrGoodsPhotoByGoodsId(goodsId);
	}
}
