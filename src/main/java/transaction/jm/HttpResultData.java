package transaction.jm;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class HttpResultData {
    private String code;
    private String message;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

}
