package site.joshua.acs.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    private String loginId; //로그인 아이디

    @NotEmpty
    private String password; //로그인 패스워드

    @NotEmpty
    private String userName; //사용자 이름

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

}
