package site.pushy.server2;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pushy
 * @since 2019/2/10 21:14
 */
@RestController
public class MainController {

    @GetMapping("")
    public String main() {
        return "This is Server 2";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, @RequestParam String name) {
        request.getSession().setAttribute("curUser", name);
        return "【Server 2】success";
    }

    @GetMapping("/users/me")
    public String getMyInfo(HttpServletRequest request) {
        System.out.println("【server 2】session id => " + request.getSession().getId());
        Object attr = request.getSession().getAttribute("curUser");
        if (attr == null) {
            return "请登录";
        } else {
            return "【Server 2】" + String.valueOf(attr);
        }
    }

}