package com.qingdao2world.basic.api;

import com.qingdao2world.basic.api.domain.NrShop;
import com.qingdao2world.basic.api.domain.dto.ShopTokenDTO;
import com.qingdao2world.basic.api.factory.RemoteShopFallbackFactory;
import com.qingdao2world.common.core.constant.SecurityConstants;
import com.qingdao2world.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 店铺API
 */
@FeignClient(contextId = "remoteShopService", value = "o2o-new-retail-modules-basic", fallbackFactory = RemoteShopFallbackFactory.class, path = "/api/shop")
public interface RemoteShopService {
    @GetMapping("/selectAllShopTokenList")
    R<List<ShopTokenDTO>> selectAllShopTokenList(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/selectShopByShopCode")
    R<ShopTokenDTO> selectShopByShopCode(@RequestParam("shopCode") String shopCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping("/selectShopById")
    R<NrShop> selectShopById(@RequestParam("shopId") Long shopId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
