package db;

import model.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleDatabaseTest {

    private final String URL = "jdbc:mysql://127.0.01:3306/codesquad";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    ArticleDatabase articleDatabase;

    @BeforeEach
    public void beforeEach() {
        articleDatabase = new ArticleDatabase(URL, USERNAME, PASSWORD);
        articleDatabase.deleteAllArticle();
    }

    @AfterEach
    public void afterEach() {
        articleDatabase.deleteAllArticle();
    }

    @Test
    @DisplayName("게시글 아이디로 사용자 데이터베이스에서 게시글 데이터를 얻어야 한다.")
    public void findTest() {
        // given
        Article article = new Article( "작성자입니다", "제목입니다", "내용입니다");

        // when
        articleDatabase.add(article);
        Article actualArticle = articleDatabase.findByIndex(1);

        // then
        assertThat(article.getWriter()).isEqualTo("작성자입니다");
        assertThat(article.getTitle()).isEqualTo("제목입니다");
        assertThat(article.getContents()).isEqualTo("내용입니다");


    }

    @Test
    @DisplayName("게시글 데이터베이스에서 모든 게시글 데이터를 얻어야 한다.")
    public void findAllTest() {
        // given
        Article articleA = new Article("작성자입니다A", "제목입니다A", "내용입니다A");
        Article articleB = new Article("작성자입니다B", "제목입니다B", "내용입니다B");
        Article articleC = new Article("작성자입니다C", "제목입니다C", "내용입니다C");
        articleDatabase.add(articleA);
        articleDatabase.add(articleB);
        articleDatabase.add(articleC);

        List<Article> expectedArticles = new ArrayList<>();
        Article expectedArticleA = new Article(1, "작성자입니다A", "제목입니다A", "내용입니다A");
        Article expectedArticleB = new Article(2, "작성자입니다B", "제목입니다B", "내용입니다B");
        Article expectedArticleC = new Article(3, "작성자입니다C", "제목입니다C", "내용입니다C");
        expectedArticles.add(expectedArticleA);
        expectedArticles.add(expectedArticleB);
        expectedArticles.add(expectedArticleC);

        // when
        List<Article> actualArticles = articleDatabase.findAll();

        // then
        assertThat(actualArticles).usingRecursiveComparison().isEqualTo(expectedArticles);

    }

    @Test
    @DisplayName("게시글 아이디로 게시글 데이터베이스에서 게시글 데이터를 제거해야한다.")
    public void deleteTest() {
        // given
        Article article = new Article( "작성자입니다", "제목입니다", "내용입니다");

        // when
        articleDatabase.add(article);
        Article actualArticle = articleDatabase.findByIndex(1);
        assertThat(actualArticle).isNotNull();
        articleDatabase.deleteArticleById(1);
        actualArticle = articleDatabase.findByIndex(1);

        // then
        assertThat(actualArticle).isNull();
    }
}
