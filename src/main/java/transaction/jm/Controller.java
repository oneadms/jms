package transaction.jm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@RestController
public class Controller {
    @Autowired
    private LoginService loginService;

    ExecutorService executor = Executors.newFixedThreadPool(100);
    private HttpResultData result;

    @PostMapping("/register")
    public HttpResultData register(@RequestBody @Valid RegisterBean paramMap) {
        FutureTask<HttpResultData> futureTask = new FutureTask<HttpResultData>(() -> loginService.register(paramMap));
        executor.submit(futureTask);
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/login")
    public void login(@RequestParam(name = "id") String id, @RequestParam(name = "pwd") String pwd,
                      @RequestParam(name = "captcha") String captcha) {
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        loginService.getCaptcha(response.getOutputStream(), request.getSession());

    }
    @RequestMapping(value = "/*/chnehuahong/{var}",method = RequestMethod.GET,params = "!admin")
    public String pathVar(@PathVariable(value = "var") String path){

        return path;
    }
}
