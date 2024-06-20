package com.qingdao2world.basic.api;

import com.qingdao2world.basic.api.domain.dto.SpShipPriceTemplateDetailDTO;
import com.qingdao2world.basic.api.factory.RemoteSpShipPriceTemplateFallfactory;
import com.qingdao2world.common.core.constant.SecurityConstants;
import com.qingdao2world.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.qingdao2world.common.core.constant.ServiceNameConstants.BASIC_SERVICE;

@FeignClient(contextId = "remoteSpShipPriceTemplateService", value = BASIC_SERVICE, fallbackFactory = RemoteSpShipPriceTemplateFallfactory.class, path = "/api/spShipPriceTemplate")
public interface RemoteSpShipPriceTemplateService {
    @GetMapping("/getTemplateById")
    R<List<SpShipPriceTemplateDetailDTO>> getTemplateById(@RequestParam("templateId") Long templateId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
