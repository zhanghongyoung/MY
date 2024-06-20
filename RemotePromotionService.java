package com.qingdao2world.basic.api;

import com.qingdao2world.basic.api.domain.ScPromotion;
import com.qingdao2world.basic.api.domain.dto.NrGoodsSelectDTO;
import com.qingdao2world.basic.api.factory.RemotePromotionFallbackFactory;
import com.qingdao2world.common.core.constant.SecurityConstants;
import com.qingdao2world.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.qingdao2world.common.core.constant.ServiceNameConstants.BASIC_SERVICE;

@FeignClient(contextId = "remotePromotionService", name = BASIC_SERVICE, fallbackFactory = RemotePromotionFallbackFactory.class, path = "/api/promotion")
public interface RemotePromotionService {

    @RequestMapping("/selectPromotionByStatus")
    R<List<ScPromotion>> selectPromotionByStatus(@RequestParam("statusList") List<Integer> statusList, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @RequestMapping("/updatePromotionByStatus")
    R<String> updatePromotionByStatus(@RequestBody List<ScPromotion> scPromotionList, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @RequestMapping("/updateSpIndexTemplateFloor")
    R<String> updateSpIndexTemplateFloor(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
