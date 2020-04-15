package transaction.jm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author chinav578
 */
@Data
@NotNull(message = "用户名密码昵称邮箱不能为空")
public class RegisterBean {
    @NotNull(message = "用户名不能为空")
    private String user;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "您的昵称不能为空")
    private String name;
    @NotNull(message = "您的邮箱不能为空")
    private String email;

}
