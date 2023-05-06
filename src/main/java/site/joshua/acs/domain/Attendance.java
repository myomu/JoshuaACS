package site.joshua.acs.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Attendance {

    @Id @GeneratedValue
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // Foreign Key
    private Member member;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private LocalDateTime attendance_date;

}