package controller;

import bean.Comment;
import bean.User;
import service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by Dragon on 2016/12/1.
 * 评论
 */
@WebServlet(
        name = "CommentServlet",
        urlPatterns = {"/comment"}
)
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        PrintWriter out = response.getWriter();
        if (user == null){
            out.print("-1");
            return;
        }
        String txt = request.getParameter("txt");
        String articleId = request.getParameter("articleId");

        CommentService commentService = new CommentService();
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setUserId(user.getId());
        comment.setArticleId(articleId);
        comment.setContent(txt);
        commentService.saveComment(comment);
        out.print("1");
        System.out.println("评论保存成功");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
