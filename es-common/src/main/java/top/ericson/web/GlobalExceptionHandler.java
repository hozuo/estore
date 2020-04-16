package top.ericson.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import top.ericson.exception.ServiceException;
import top.ericson.vo.JsonResult;

/**
 * @author Ericson
 * @class GlobalExceptionHandler
 * @date 2020/04/04 17:14
 * @version 1.0
 * @description 全局异常处理类
 */
// 该注解表示开启全局异常捕获
@ControllerAdvice
// 定义private Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
@Slf4j
public class GlobalExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public JsonResult doHandleServiceException(ServiceException e) {
        log.error(e.getMessage()+"捕获了ServiceException");
        if (Integer.valueOf(1).equals(e.getState())) {
            e.printStackTrace();
        }
        return JsonResult.exce(e);
    }

    @ResponseBody
    // 指定异常捕获的目标类型
    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandleRuntimeException(RuntimeException e) {
        // 也可以写日志
        log.error(e.getMessage()+"捕获了RuntimeException");
        e.printStackTrace();
        // 封装异常信息
        return JsonResult.exce(e);
    }

}
