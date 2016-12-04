package test;

import bean.Article;

import service.ArticleService;
import util.DataBaseUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by Dragon on 2016/11/28.
 *  测试：给文章插入操作
 */
public class TestInsertOperation {
    public void insertArticle(){
        String sql = "INSERT INTO t_article(id,header,name,content,author,"
                + "description,is_published,is_delete,create_time,update_time"
                + ",user_id,category_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        String id = UUID.randomUUID().toString(); //主键
        String header = "Java Web实用技术";
        String name  = "如何将MyEclipse项目导入eclipse";
        String content = "我们经常会在网上下载一些开源项目，或者从别的地方迁移一些项目进来，" +
                "但经常会发现导入后各种报错。这是初学java肯定会遇到的问题，本文对一些常见的处理方案做一个总结。" +
                "（本文将MyEclipse项目导入eclipse的过程为例，其他情况也可参考这个流程）";
        String author = "Jack";
        String description = "解决项目导入的冲突问题...";
        int isPublished = 1 ;
        int isDelete = 0;
        String create_time = "2016-10-19 10:43:10";
        String update_time = "2016-10-19 10:43:10";
        String userId = "319600c3-550a-4f9f-80cf-deebe2376528";
        int categoryId = 2;
        DataBaseUtils.update(sql, id, header, name, content, author, description, isPublished,
                isDelete, create_time, update_time, userId, categoryId);
        System.out.println("新增成功");
    }

    public void getArticle(){
        String sql = "select * from t_article where id = ?";
        Article article = DataBaseUtils.queryForBean(sql, Article.class, "8c7244c6-56b9-450e-8d1d-567f4b265497");
        System.out.println(article);
    }

    public void getCategoryList(){
        String sql = "select * from t_category where 1 = 1";
        List list = DataBaseUtils.queryForList(sql);
        System.out.println(list);
    }

    public static void main(String[] args){
        ArticleService articleService = new ArticleService();
        List list = articleService.getArticlesByCategoryId(2,0,10);
        System.out.println(list);
    }
}
