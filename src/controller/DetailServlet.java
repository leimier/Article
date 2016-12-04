package controller;

import bean.Article;
import bean.Comment;
import service.ArticleService;
import service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Dragon on 2016/11/29.
 * 文章详情页控制
 */
@WebServlet(
        name = "DetailServlet",
        urlPatterns = {"/detail.jsp"}
)
public class DetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 根据id查找到文章并传递给request
        String id = request.getParameter("id");
        ArticleService articleService = new ArticleService();
        Map<String, Object> map = articleService.getContentByArticleId(id);
        request.setAttribute("article", map);

        // 获取文章评论
        CommentService commentService = new CommentService();
        List<Map<String, Object>> list = commentService.getCommentsByArticleId(id);
        request.setAttribute("comments", list);

        request.getRequestDispatcher("WEB-INF/jsp/detail.jsp").forward(request, response);
    }
}
