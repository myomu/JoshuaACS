package site.joshua.acs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING) // EnumType
    private Gender gender; // 성별 [MAN, WOMAN]

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id") // Foreign Key
    private Group group;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Attendance> attendances = new ArrayList<>();

    public void createMember(String name, int age, Gender gender, Group group) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.group = group;
    }

    public void editMember(String name, int age, Gender gender, Group group) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.group = group;
    }

    public void setMemberId(Long id) {
        this.id = id;
    }
}
