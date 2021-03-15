package com.dxy.commerce.product.common;

/**
 * 公共返回code码定义
 * @author dingxy
 * @date 2021/2/27 4:14 下午
 * @return 
 */
public enum ResultCode {
    SUCCESS(0, "成功"),

    // rest 请求错误码
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_QUERY_TYPE_WRONG(10002, "请求类型错误！"),
    DATA_IS_EXIST(-1, "该品牌已录入，请您重新输入"),
    DATA_NEED_DISTINCT(-1, "修改后的品牌名称重复，请您重新输入"),

    //业务错误类型
    SYSTEM_EXCEPTION_GRAMMAR(20001, "语法解析错误"),
    SYSTEM_EXCEPTION_MONITOR_EXIST(20002, "预警名称已经存在"),
    SYSTEM_EXCEPTION_MONITOR_RELATION_NOT_EXIST(20003, "暂未开放关联方的预警监控"),

    //权限错误码
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    PERMISSION_TOKEN_IS_WRONG(70002, "TOKEN 信息错误，请检查后重试"),
    PERMISSION_TOKEN_IS_NULL(70003, "TOKEN 不存在，请联系管理员！"),
    PERMISSION_BOARD_IS_FORBID(70004, "您暂无访问权限"),

    //token信息错误
    TOKEN_IS_ERROR(80000, "TOKEN 信息有错误，请检查后重试！"),

    //系统错误码
    SENSEDEAL_QUESTIONED(90001, "系统开了个小差，请稍后重试！");

    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.message;
            }
        }

        return name;
    }

    public static Integer getCode(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.code;
            }
        }

        return null;
    }

    public String toString() {
        return this.name();
    }
}
