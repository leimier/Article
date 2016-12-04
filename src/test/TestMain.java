package test;


import bean.Comment;
import service.CommentService;
import util.TableUtils;

/**
 * Created by Dragon on 2016/11/28.
 *
 */
public class TestMain {
    public static void main(String[] args){
//        String sql = TableUtils.getCreateTableSQL(Comment.class);
//        System.out.println(sql);

        CommentService commentService = new CommentService();
        System.out.print(commentService.getCommentsByArticleId("8c7244c6-56b9-450e-8d1d-567f4b265497"));
    }
}
