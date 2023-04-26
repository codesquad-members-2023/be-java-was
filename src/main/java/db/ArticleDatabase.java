package db;

import model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDatabase {
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    private Logger logger = LoggerFactory.getLogger(ArticleDatabase.class);
    public ArticleDatabase(String URL, String USERNAME, String PASSWORD) {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }
    public void add(Article article) {
        String sql = "insert into articles(articleId, writer, title, contents) values (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            int articleId = getArticleSize() + 1;

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, articleId);
            pstmt.setString(2, article.getWriter());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getContents());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to add of DB :" + e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    public Article findByIndex(int index) {
        String sql = "select articleId, writer, title, contents from articles where articleId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, index);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int articleId = rs.getInt(1);
                String writer = rs.getString(2);
                String title = rs.getString(3);
                String contents = rs.getString(4);
                return new Article(articleId, writer, title, contents);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Failed to findByIndex of DB :" + e);
            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Article> findAll() {
        String sql = "select articleId, writer, title, contents from articles";
        Connection conn = null;
        Statement stmt  = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int articleId = rs.getInt(1);
                String writer = rs.getString(2);
                String title = rs.getString(3);
                String contents = rs.getString(4);
                articles.add(new Article(articleId, writer, title, contents));
            }
            return articles;
        } catch (SQLException e) {
            logger.error("Failed to findByIndex of DB :" + e);
            return null;
        } finally {
            close(conn, stmt , rs);
        }
    }

    private int getArticleSize() throws SQLException {
        String sql = "select count(*) from articles";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            logger.error("Failed to get size of DB :" + e);
            throw e;

        } finally {
            close(conn, stmt, null);
        }
    }

    public int deleteArticleById(int id) {
        String sql = "DELETE from articles where articleId = ? ";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to findByIndex of DB :" + e);
            return -1;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int deleteAllArticle() {
        String sql = "DELETE from articles";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to findByIndex of DB :" + e);
            return -1;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.error("Failed to connect DB :" + e);
            return null;
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            logger.error("Failed to close ResultSet");
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            logger.error("Failed to close Statement");
        }
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("Failed to close Connection");
        }
    }
}
