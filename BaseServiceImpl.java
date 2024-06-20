package com.qingdao2world.basic.service.impl;

import cn.hutool.core.lang.Dict;

public class BaseServiceImpl {
    protected static Dict getBasicDict(String message, boolean success) {
        int code = 200;
        if (!success) {
            code = 202;
        }
        return new Dict().set("message", message).set("success", success).set("code", code);
    }
    protected int getIsShow(boolean checked) {
        int isShow = 0;
        if (checked) {
            isShow = 1;
        }
        return isShow;
    }

    protected static Dict getBasicDictError(String msg) {
        return getBasicDict(msg, false);
    }

}
