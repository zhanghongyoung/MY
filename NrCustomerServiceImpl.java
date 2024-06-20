package com.qingdao2world.basic.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.qingdao2world.basic.api.domain.NrCustomer;
import com.qingdao2world.basic.mapper.NrCustomerMapper;
import com.qingdao2world.basic.service.INrCustomerService;
import com.qingdao2world.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NrCustomerServiceImpl extends BaseService implements INrCustomerService {

    @Autowired
    private NrCustomerMapper nrCustomerMapper;

    @Override
    public int insertCustomer(NrCustomer nrCustomer) {
        return nrCustomerMapper.insertCustomer(nrCustomer);
    }

    /**
     * 查询客户列表
     * @return
     */
    @Override
    public List<NrCustomer> customerList4Select() {
        return nrCustomerMapper.customerList4Select();
    }

    @Override
    public R<NrCustomer> selectById(Long id) {
        NrCustomer res = nrCustomerMapper.selectById(id);
        return ObjectUtil.isNotNull(res) ? R.ok(res) : R.fail("没有查到id为"+id+"的客户");
    }


    /**
     * 查询客户
     *
     * @param id 客户主键
     * @return 客户
     */
    @Override
    public NrCustomer selectNrCustomerById(Long id) {
        return nrCustomerMapper.selectNrCustomerById(id);
    }

    /**
     * 查询客户列表
     *
     * @param nrCustomer 客户
     * @return 客户
     */
    @Override
    public List<NrCustomer> selectNrCustomerList(NrCustomer nrCustomer) {
        return nrCustomerMapper.selectNrCustomerList(nrCustomer);
    }

    /**
     * 新增客户
     *
     * @param nrCustomer 客户
     * @return 结果
     */
    @Override
    public int insertNrCustomer(NrCustomer nrCustomer) {
        setInsertBaseEntity(nrCustomer);
        return nrCustomerMapper.insertNrCustomer(nrCustomer);
    }

    /**
     * 修改客户
     *
     * @param nrCustomer 客户
     * @return 结果
     */
    @Override
    public int updateNrCustomer(NrCustomer nrCustomer) {
        setUpdateBaseEntity(nrCustomer);
        return nrCustomerMapper.updateNrCustomer(nrCustomer);
    }

    /**
     * 批量删除客户
     *
     * @param ids 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteNrCustomerByIds(Long[] ids) {
        return nrCustomerMapper.deleteNrCustomerByIds(ids);
    }

    /**
     * 删除客户信息
     *
     * @param id 客户主键
     * @return 结果
     */
    @Override
    public int deleteNrCustomerById(Long id) {
        return nrCustomerMapper.deleteNrCustomerById(id);
    }
}
