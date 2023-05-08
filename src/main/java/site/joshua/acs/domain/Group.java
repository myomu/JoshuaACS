package site.joshua.acs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String group_name;

    @OneToOne(mappedBy = "group", fetch = LAZY)
    private Member member;

    public void createGroup(String group_name) {
        this.group_name = group_name;
    }
}
