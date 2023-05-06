package site.joshua.acs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Minutes {

    @Id @GeneratedValue
    @Column(name = "minutes_id")
    private Long id;

    private String name;
    private String url;

}
