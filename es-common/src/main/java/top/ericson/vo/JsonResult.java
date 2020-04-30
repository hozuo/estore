package top.ericson.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author Ericson
* @class JsonResult
* @date 2020/03/31 19:52
* @version 1.0
* @description api调用返回的json通用格式
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {
    private String status;
    private String msg;
    private Object data;

    /**只返回成功信息*/
    public static JsonResult success() {
        return new JsonResult(ResultCode.SUCCESS.getValue(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**成功后返回数据*/
    public static JsonResult success(Object data) {
        return new JsonResult(ResultCode.SUCCESS.getValue(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**成功后返回信息和数据*/
    public static JsonResult success(String msg,
        Object data) {
        return new JsonResult(ResultCode.SUCCESS.getValue(), msg, data);
    }

    /**只返回失败信息*/
    public static JsonResult fail() {
        return new JsonResult(ResultCode.FAIL.getValue(), ResultCode.FAIL.getMsg(), null);
    }

    /**失败后返回自定义信息*/
    public static JsonResult msg(String msg) {
        return new JsonResult(ResultCode.FAIL.getValue(), msg, null);
    }
    
    /**出现异常时时调用*/
    public static JsonResult exce(Throwable t) {
        return new JsonResult(ResultCode.EXCEPTION.getValue(), t.getMessage(), null);
    }
    
    public static JsonResult build(ResultCode rc) {
        return new JsonResult(rc.getValue(), rc.getMsg(), null);
    }

}
