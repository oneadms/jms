package transaction.jm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author chinav578
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionInterceptor {
     @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(HttpServletRequest request, Exception e) {
        String failMsg = null;
         if (e instanceof MethodArgumentNotValidException) {
             // 拿到参数校验具体异常信息提示
             failMsg = Objects.requireNonNull(((MethodArgumentNotValidException) e
             ).getBindingResult().
                     getFieldError()).getDefaultMessage();
         } else {
             if (e instanceof UnsatisfiedServletRequestParameterException) {

                 return "拒绝连接";
             }
         }
        return failMsg;
// 直接吐回给前端
    }
}