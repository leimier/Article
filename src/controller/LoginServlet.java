package controller;

import bean.User;
import service.LoginService;
import util.DataBaseUtils;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Dragon on 2016/11/28.
 * 登陆
 */
@WebServlet(
    name = "LoginServlet",
    urlPatterns = {"/login.jsp", "/login"}
)
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();   // 获取输出流

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            out.print("-1");   // 错误码-1： 用户名和密码不能为空
        }else {
            LoginService loginService = new LoginService();
            User user = loginService.getUser(username);
            if (user == null){
                out.print("-2");  //错误码-2：用户不存在
            }else {
                if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())){
                    out.print("-3"); //错误码-3：用户名或密码错误
                }else {
                    //如果能到这一步，就说明用户的确存在，而且账号密码也正确。
                    // 那么就把user放在session中
                    out.print("1");
                    System.out.println("用户 " + username + " 登陆成功");
                    request.getSession().setAttribute("username", username);
                    request.getSession().setAttribute("user", user);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }

}
