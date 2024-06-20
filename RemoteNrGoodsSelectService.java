package com.qingdao2world.basic.api;

import com.qingdao2world.basic.api.domain.NrGoodsSelect;
import com.qingdao2world.basic.api.domain.dto.NrGoodsSelectDTO;
import com.qingdao2world.basic.api.factory.RemoteNrGoodsSelectFallbackFactory;
import com.qingdao2world.common.core.constant.SecurityConstants;
import com.qingdao2world.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteNrGoodsSelectService", value = "o2o-new-retail-modules-basic", fallbackFactory = RemoteNrGoodsSelectFallbackFactory.class, path = "/api/goodsSelect")
public interface RemoteNrGoodsSelectService {

    @PostMapping("/shopAutoSelectGoods")
    R<Integer> shopAutoSelectGoods(@RequestBody String stockJson, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @RequestMapping("/selectNrGoodsSelectByGoodsIdAndShopIdAndWarehouseId")
    R<NrGoodsSelect> selectNrGoodsSelectByGoodsIdAndShopIdAndWarehouseId(@RequestParam("goodsId") Long goodsId, @RequestParam("shopId") Long shopId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @RequestMapping("/selectGoodsStockByWarehouse")
    R<List<NrGoodsSelectDTO>> selectGoodsStockByWarehouse(@RequestParam("goodsId") Long goodsId, @RequestParam("warehouseId") Long warehouseId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @RequestMapping("/updateBatchStock")
    R<String> updateBatchStock(@RequestParam("warehouseId") Long warehouseId, @RequestParam("goodsId") Long goodsId, @RequestParam("goodsBarcode") String goodsBarcode, @RequestParam("stock") Long stock, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
