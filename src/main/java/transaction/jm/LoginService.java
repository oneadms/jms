package transaction.jm;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author chinav578
 */
@Component
public class LoginService {
    private String captcha;

    private static Jedis jedis;

    static {
        jedis = new Jedis();
    }

    public LoginService() {
        System.out.println("ｌｏｇｉｎｓｅｒｖｉｃｅ创建完成");

    }

    public void login(String name, String pwd) {

    }
    public <K,V> HashMap<K,V> getMap(K k,V v){
        HashMap<K,V> hashMap=new HashMap<>(32);
        hashMap.put(k,v);
        return hashMap;
    }
    public HttpResultData register(@RequestBody RegisterBean paramMap) {
        String name = paramMap.getName();
        String password = paramMap.getPassword();
        String user = paramMap.getUser();
        String email=paramMap.getEmail();
        System.out.println(paramMap.toString());
        Boolean flags = jedis.exists(user);
        String res;
        HttpResultData resultData=new HttpResultData();
        if (flags) {
            resultData.setCode("fail");
            res = "该用户已经存在";
        } else {
            jedis.hmset(user,getMap("name",name));
            jedis.hmset(user,getMap("password",password));
            jedis.hmset(user,getMap("email",email));
            resultData.setCode("suceess");
            res="注册成功，hi，欢迎你的加入233";
        }
        resultData.setMessage(res);
        resultData.setCreateTime(new Date());

        System.out.println(resultData);
        return resultData;
    }

    public void getCaptcha(ServletOutputStream outputStream,HttpSession session) throws IOException {
        final int width=68;
        final int height=34;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //设置背景
        g.setBackground(Color.white);
        // 设置字体
        g.clearRect(0, 0, width, height);
        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        g.setFont(font);
        String captcha = getRandString();
        Random random=new Random();
        char[] captchaArray=captcha.toCharArray();
        for (int i = 0; i <captchaArray.length; i++) {

            g.setPaint(getRandColor());
            g.drawString(captchaArray[i]+"",i*11+5,20);
        }
        //干扰线
        g.setPaint(Color.black);
        Font lineFont = new Font("Times New Roman", Font.PLAIN, 10);
        g.setFont(lineFont);
        final int line=45;
        for (int i = 0; i < line; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
        image.flush();
        ImageIO.write(image,"JPG",outputStream);

        outputStream.flush();
        session.setAttribute("captcha",captcha);
        outputStream.close();


    }

    public String getRandString(){
        String[] captchaArrays={"a","b","c","d","e","f","g","h","i","j","k","l","o","p","q","r","s","t","m","n","u","v","w","x",
                "y","z","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","O","P",
                "Q","M","N","R","S","T","V","Z","X","Y"};
        Random random=new Random();

        StringBuilder result=new StringBuilder();
        while (result.length()<6){
            final int index=random.nextInt(captchaArrays.length);
            result.append(captchaArrays[index]);
        }
        return  result.toString();

    }

    /**
     *
     * @return GetRandomColor 生成随机颜色
     */
    public Color getRandColor(){
        int r;
        int g;
        int b;
        Random random=new Random();
        r=(random.nextInt(110));
        g=(random.nextInt(50));
        b=(random.nextInt(50));

        return new Color(r,g,b);
    }


}
