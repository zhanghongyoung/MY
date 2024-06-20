package com.qingdao2world.basic.service.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.qingdao2world.basic.api.domain.NrGoodsAccessory;
import com.qingdao2world.basic.api.domain.dto.NrGoodsRecordDTO;
import com.qingdao2world.basic.mapper.NrAttachmentMapper;
import com.qingdao2world.basic.service.BaseVarService;
import com.qingdao2world.basic.service.INrAttachmentService;
import com.qingdao2world.basic.service.UploadService;
import com.qingdao2world.common.core.enums.AttachTypeEnum;
import com.qingdao2world.common.core.enums.ValidUnitEnum;
import com.qingdao2world.common.core.exception.ServiceException;
import com.qingdao2world.common.core.utils.ImageHtUtil;
import com.qingdao2world.common.core.utils.StringUtils;
import com.qingdao2world.common.core.utils.SysUtil;
import com.qingdao2world.common.core.utils.redis.RedisKeyStore;
import com.qingdao2world.common.core.utils.redis.RedisUtil;
import com.qingdao2world.common.core.web.domain.BaseEntity;
import com.qingdao2world.common.security.utils.SecurityUtils;
import com.qingdao2world.warehouse.api.domain.NrWarehouseReceiptNormalDetail;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author CHEN HUI
 * @Description: 公共服务类，负责处理公共方法赋值等内容
 * @Title: BaseService
 * @Copyright: Copyright (c) 2022
 * @Company: 西海岸跨境
 * @Created on 2022/10/14 14:05
 */
@Slf4j
@Service("BaseService")
public class BaseService {


    @Autowired
    private UploadService uploadService;
    @Autowired
    private BaseVarService baseVarService;
    @Autowired
    private NrAttachmentMapper nrAttachmentMapper;
    @Autowired
    private INrAttachmentService nrAttachmentService;
    @Autowired
    protected JedisPool jedisPool;


    protected void insertAttachment(String fileUrl, Long recordId, AttachTypeEnum attachTypeEnum) {
        // 删除已有的附件
        nrAttachmentMapper.deleteDcAttachmentByRecordIdAndModule(recordId, attachTypeEnum.moduleName);
        if (StrUtil.isNotEmpty(fileUrl)) {
            nrAttachmentService.insertNrAttachment(fileUrl, recordId, attachTypeEnum);
        }
    }

    /**
     * insert 时基础bean赋值设置
     *
     * @param baseEntity
     */
    protected void setInsertBaseEntity(BaseEntity baseEntity) {
        baseEntity.setCreateBy(SecurityUtils.getUserId().toString());
        baseEntity.setUpdateBy(SecurityUtils.getUserId().toString());
    }

    /**
     * update 时基础bean赋值设置
     *
     * @param baseEntity
     */
    protected void setUpdateBaseEntity(BaseEntity baseEntity) {
        baseEntity.setUpdateBy(SecurityUtils.getUserId().toString());
    }

    protected static Dict getBasicDict(String msg, boolean success) {
        int code = 200;
        if (!success) {
            code = 202;
        }
        return new Dict().set("message", msg).set("success", success).set("code", code);
    }
    protected Dict Fail(String message) {
        return getBasicDict(message, false);
    }
    protected Dict Success() {
        return getBasicDict("", true);
    }
    protected Dict Success(String message) {
        return getBasicDict(message, true);
    }
    protected String getProjectStorePath() {
        return baseVarService.getSellerVar("productTempPath");
    }

    protected String getUploadPattern() {
        return baseVarService.getCommonVar("fileUploadPattern");
    }

    protected Dict validFileSize(long fileSize) {
        String msg = "";
        long maxFileSize = Convert.toLong(baseVarService.getCommonVar("maxFileSize"));
        if ((fileSize / 1024) > maxFileSize) {
            msg = "抱歉，文件不能超过200kb";
            log.error(msg);
            throw new ServiceException(msg);
        }
        return getBasicDict(msg, true);
    }

    /**
     * 阿里云上传filekey必须传，即文件路径
     *
     * @param uploadPattern
     * @param fileKey
     * @param file
     * @return
     */
    protected String uploadFile(String uploadPattern, String fileKey, File file) throws JSONException {
        String fileUrl = "";
        if (file.exists()) {
            String path = "";
            if (StrUtil.equals(uploadPattern, NrGoodsAccessory.AccessoryUploadPatternEnum.fastdfs.name())) {
                path = uploadService.uploadPicToFastDFS(FileUtil.readBytes(file),
                        com.qingdao2world.common.core.utils.file.FileUtil.getExtension(file.getName()));
            } else if (StrUtil.equals(uploadPattern, NrGoodsAccessory.AccessoryUploadPatternEnum.alioss.name())) {
                path = uploadService.uploadPicToAliOss(FileUtil.readBytes(file), fileKey);
            }
            String imgDomain = baseVarService.getCommonVar("fileDomain");
            if (!StringUtils.isEmpty(path) && !StringUtils.isEmpty(imgDomain)) {
                fileUrl = imgDomain + path;
            }
        }
        return fileUrl;
    }
    protected int getIsShow(boolean checked) {
        int isShow = 0;
        if (checked) {
            isShow = 1;
        }
        return isShow;
    }

    protected void removeApp(String channelid) {
        RedisUtil redisUtil = getRedisUtil();
        if (StrUtil.isEmpty(channelid)) {
            redisUtil.delPreKey(RedisKeyStore.OBJECT_APP);
        } else {
            String key = RedisKeyStore.getKey(RedisKeyStore.OBJECT_APP, channelid);
            redisUtil.del(key);
        }
    }
    private RedisUtil getRedisUtil() {
        RedisUtil redisUtil = new RedisUtil(jedisPool);
        return redisUtil;
    }

    public Dict uploadPic(MultipartFile file, String imgTypeEnum, boolean isNeedSmall, boolean isNeedMedium) throws JSONException, IOException {

        String msg = "";
        boolean success = false;
        int imgId = 0;
        String originRemoteUrl = "";
        String smallRemoteUrl = "";
        String mediumRemoteUrl = "";
        // try {
        long fileSize = file.getSize();
        Dict validFileSizeDt = validFileSize(fileSize);
        if (Boolean.TRUE.equals(validFileSizeDt.getBool("success"))) {
            String originalFilename = file.getOriginalFilename();
            String imgExt = com.qingdao2world.common.core.utils.file.FileUtil.extractFileExt(originalFilename);
            String projectPath = getProjectStorePath();
            String imgMain = StrUtil.format("img/{}/", imgTypeEnum);
            String originPath = StrUtil.format("{}{}.{}", imgMain, SysUtil.getId(), imgExt);
            File originFile = FileUtil.file(projectPath, originPath);
            FileUtil.writeBytes(file.getBytes(), originFile);
            if (originFile.exists()) {

                String uploadPattern = getUploadPattern();
                originRemoteUrl = uploadFile(uploadPattern, originPath, originFile);

                if (isNeedSmall) {
                    String smallPath = imgMain + SysUtil.getId() + "." + imgExt;
                    File smallFile = FileUtil.file(projectPath, smallPath);
                    int smallWidth = 240;
                    if (ImageHtUtil.scaleWithWidth(originFile, smallFile, smallWidth)) {
                        smallRemoteUrl = uploadFile(uploadPattern, smallPath, smallFile);
                    } else {
                        smallRemoteUrl = originRemoteUrl;
                    }
                } else {
                    smallRemoteUrl = originRemoteUrl;
                }

                if (isNeedMedium) {
                    int mediumWidth = 480;
                    String mediumPath = imgMain + SysUtil.getId() + "." + imgExt;
                    File mediumFile = FileUtil.file(projectPath, mediumPath);
                    if (ImageHtUtil.scaleWithWidth(originFile, mediumFile, mediumWidth)) {
                        mediumRemoteUrl = uploadFile(uploadPattern, mediumPath, mediumFile);
                    } else {
                        mediumRemoteUrl = originRemoteUrl;
                    }
                } else {
                    mediumRemoteUrl = originRemoteUrl;
                }

                NrGoodsAccessory accessory = new NrGoodsAccessory();
                accessory.setDelFlag("0");
                setInsertBaseEntity(accessory);
                accessory.setExt(imgExt);
                accessory.setImgName(originalFilename);
                accessory.setSize(Convert.toDouble(fileSize));
                accessory.setSmallPath(smallRemoteUrl);
                accessory.setMediumPath(mediumRemoteUrl);
                accessory.setOriginPath(originRemoteUrl);
                accessory.setImgType(imgTypeEnum);
                accessory.setUploadPattern(uploadPattern);
                int i = uploadService.sellerInsertAccessory(accessory);
                if (i > 0) {
                    imgId = accessory.getId().intValue();
                }
                success = true;
            } else {
                msg = "图片上传失败！";
                throw new ServerException(msg);
            }
        } else {
            msg = validFileSizeDt.getStr("message");
            throw new ServerException(msg);
        }
        // } catch (Exception e) {
        //     msg = e.getMessage();
        //     log.error(msg);
        // }

        return getBasicDict(msg, success).set("imgId", imgId).set("url", originRemoteUrl).set("smallUrl",
                smallRemoteUrl);
    }

    protected boolean isNumber(String str){
        Pattern pattern= Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        return match.matches();
    }


    /**
     * 正整数校验
     * @param num
     * @return
     */
    protected boolean isPositiveNumber(String num){
        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }


    /**
     * 是否含有单引号
     * @param s
     * @return
     */
    protected boolean isSingleQuotation(String s){
        Pattern pattern = Pattern.compile("^.*'.*$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
