package service;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {
    UserService service;
    User sampleUser;

    @BeforeEach
    void init() {
        service = new UserService();
    }
    
    @Test
    @DisplayName("member가 1개 성공적으로 저장되면 Database에 저장된 총 값이 1 늘어나야 한다.")
    public void joinTest() throws Exception{
        //given
        int beforeUserSize = Database.findAll().size();
        Map<String, String> params = Map.of("userId", "member",
                "password", "1234",
                "name", "user",
                "email", "asdf@naver.com");

        //when
        service.join(params);
        Collection<User> afterUsers = Database.findAll();

        //then
        assertThat(afterUsers).hasSize(beforeUserSize+1);
    }

    @Test
    @DisplayName("member가 1개 성공적으로 저장되면 Database에 저장된 총 값이 1 늘어나야 한다.")
    public void joinAMemberTest() throws Exception{
        //given
        Map<String, String> params = Map.of("userId", "member",
                "password", "1234",
                "name", "user",
                "email", "asdf@naver.com");

        //when
        service.join(params);
        User member = Database.findUserById("member");

        //then
        assertThat(member.getPassword()).isEqualTo("1234");
        assertThat(member.getName()).isEqualTo("user");
        assertThat(member.getEmail()).isEqualTo("asdf@naver.com");
    }

}
