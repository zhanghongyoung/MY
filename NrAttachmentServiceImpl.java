package com.qingdao2world.basic.service.impl;

import com.qingdao2world.basic.api.domain.NrAttachment;
import com.qingdao2world.basic.mapper.NrAttachmentMapper;
import com.qingdao2world.basic.service.INrAttachmentService;
import com.qingdao2world.common.core.enums.AttachTypeEnum;
import com.qingdao2world.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件Service业务层处理
 *
 * @author linaibo
 * @date 2023-04-12
 */
@Service
public class NrAttachmentServiceImpl extends BaseService implements INrAttachmentService {
	@Autowired
	private NrAttachmentMapper nrAttachmentMapper;

	private static final String STORAGETYPE_HTTP = "HTTP";

	/**
	 * 查询附件
	 *
	 * @param id 附件主键
	 * @return 附件
	 */
	@Override
	public NrAttachment selectNrAttachmentById(Long id) {
		return nrAttachmentMapper.selectNrAttachmentById(id);
	}

	/**
	 * 查询附件列表
	 *
	 * @param nrAttachment 附件
	 * @return 附件
	 */
	@Override
	public List<NrAttachment> selectNrAttachmentList(NrAttachment nrAttachment) {
		return nrAttachmentMapper.selectNrAttachmentList(nrAttachment);
	}

	/**
	 * 新增附件
	 *
	 * @param url
	 * @param id
	 * @param attachTypeEnum
	 * @return 结果
	 */
	@Override
	public int insertNrAttachment(String url, Long id, AttachTypeEnum attachTypeEnum) {
		NrAttachment nrAttachment = new NrAttachment();
		//隶属模块
		nrAttachment.setModule(attachTypeEnum.moduleName);
		//关联id
		nrAttachment.setRecordId(id);
		//文档类型
		nrAttachment.setDocType(attachTypeEnum.desc);
		//文档原始路径
		nrAttachment.setOrgPath(url);
		//文档路径
		nrAttachment.setDocPath(url);
		//存储方式
		nrAttachment.setStorageType(STORAGETYPE_HTTP);
		//文件类型
		String fileType = url.substring(url.lastIndexOf(".") + 1);
		nrAttachment.setFileType(fileType);
		//所属部门
		nrAttachment.setDeptId(SecurityUtils.getLoginUser().getSysUser().getDeptId());
		setInsertBaseEntity(nrAttachment);
		return nrAttachmentMapper.insertNrAttachment(nrAttachment);
	}

	/**
	 * 修改附件
	 *
	 * @param nrAttachment 附件
	 * @return 结果
	 */
	@Override
	public int updateNrAttachment(NrAttachment nrAttachment) {
		setUpdateBaseEntity(nrAttachment);
		return nrAttachmentMapper.updateNrAttachment(nrAttachment);
	}

	/**
	 * 批量删除附件
	 *
	 * @param ids 需要删除的附件主键
	 * @return 结果
	 */
	@Override
	public int deleteNrAttachmentByIds(Long[] ids) {
		return nrAttachmentMapper.deleteNrAttachmentByIds(ids);
	}

	/**
	 * 删除附件信息
	 *
	 * @param id 附件主键
	 * @return 结果
	 */
	@Override
	public int deleteNrAttachmentById(Long id) {
		return nrAttachmentMapper.deleteNrAttachmentById(id);
	}
}
