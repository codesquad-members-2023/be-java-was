package service;

import db.ArticleDatabase;
import model.Article;
import util.MyObjectMapper;

import java.util.Collection;

public class ArticleService {

    private ArticleDatabase articleDatabase;
    public ArticleService(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    public void save(Object requestParams) throws Exception {
        Article article = MyObjectMapper.readValue(requestParams, Article.class)
                .filter(Article.class::isInstance)
                .map(Article.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 파라미터입니다."));

        articleDatabase.add(article);
    }

    public Article findById(int articleId) {
        return articleDatabase.findByIndex(articleId);
    }

    public Collection<Article> findAll() {
        return articleDatabase.findAll();
    }

}
