package controller;

import service.ArticleService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Dragon on 2016/11/28.
 * 首页控制
 */
@WebServlet(
        name = "IndexServlet",
        urlPatterns = {"/index.jsp"}
)
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleService articleService = new ArticleService();
        //连载小说
        List<Map<String,Object>> articles1 = articleService.getArticlesByCategoryId(1, 0, 6);
        request.setAttribute("articles1", articles1);

        // 编程代码类
        List<Map<String,Object>> articles2 = articleService.getArticlesByCategoryId(2, 0, 6);
        request.setAttribute("articles2", articles2);

        //生活感悟
        List<Map<String,Object>> articles3 = articleService.getArticlesByCategoryId(3, 0, 6);
        request.setAttribute("articles3", articles3);
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
