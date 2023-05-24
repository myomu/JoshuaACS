package site.joshua.acs.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class MinutesFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "minutes_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;
    private String date;

    public void setMinutesFile(String uploadFileName, String storeFileName, String date) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.date = date;
    }
}
