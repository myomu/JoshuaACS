package site.joshua.acs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long age;

    @Enumerated(EnumType.STRING) // EnumType
    private Gender gender; // 성별 [MAN, WOMAN]

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "group_id") // Foreign Key
    private Group group;

    @OneToMany(mappedBy = "member")
    private List<Attendance> attendances = new ArrayList<>();
}
