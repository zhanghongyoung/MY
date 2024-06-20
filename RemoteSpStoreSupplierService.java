package com.qingdao2world.basic.api;

import com.qingdao2world.basic.api.domain.SpStoreSupplier;
import com.qingdao2world.basic.api.factory.RemoteSpStoreSupplierFallbackFactory;
import com.qingdao2world.common.core.constant.SecurityConstants;
import com.qingdao2world.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "remoteSpStoreSupplierService", value = "o2o-new-retail-modules-basic", fallbackFactory = RemoteSpStoreSupplierFallbackFactory.class, path = "/api/pStoreSupplier")
public interface RemoteSpStoreSupplierService {

    @GetMapping("/selectSpStoreSupplierById")
    R<SpStoreSupplier> selectSpStoreSupplierById(@RequestParam("supplierId") Long supplierId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    @GetMapping("/selectSpStoreSupplierByName")
    R<SpStoreSupplier> selectSpStoreSupplierByName(@RequestParam("supplierName") String supplierName, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}