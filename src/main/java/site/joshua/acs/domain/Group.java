package site.joshua.acs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "joshua_groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String group_name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public void createGroup(String group_name) {
        this.group_name = group_name;
    }

    public void editGroup(String group_name) {
        this.group_name = group_name;
    }

    public void setGroupId(Long groupId) {
        this.id = groupId;
    }
}
