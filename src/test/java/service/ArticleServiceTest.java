package service;

import db.ArticleDatabase;
import db.UserDatabase;
import model.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


class ArticleServiceTest {

    private final String URL = "jdbc:mysql://127.0.01:3306/codesquad";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    ArticleService articleService;
    ArticleDatabase articleDatabase;

    @BeforeEach
    public void beforeEach() {
        articleDatabase = new ArticleDatabase(URL, USERNAME, PASSWORD);
        articleService = new ArticleService(articleDatabase);
    }

    @AfterEach
    public void afterEach() {
        articleDatabase.deleteAllArticle();
    }

    @Test
    @DisplayName("Map 타입의 파라미터를 받아서, 데이터베이스에 게시글을 저장해야 한다.")
    public void createTest() throws Exception {

        // 파라미터의 순서는 생서자 매개변수 순서와 같아야 한다.
        Map<String, String> parameterMap = new LinkedHashMap<>();
        parameterMap.put("writer", "작성자입니다.");
        parameterMap.put("title", "제목입니다.");
        parameterMap.put("contents", "내용입니다.");

        articleService.save(parameterMap);

        Article actualArticle = articleService.findById(1);

        assertThat(actualArticle.getWriter()).isEqualTo("작성자입니다.");
        assertThat(actualArticle.getTitle()).isEqualTo("제목입니다.");
        assertThat(actualArticle.getContents()).isEqualTo("내용입니다.");
    }






}