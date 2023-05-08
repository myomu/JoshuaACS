package site.joshua.acs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AttendanceController {

    @GetMapping("/attendanceCheck")
    public String attendances() {
        return "attendance/attendanceCheck";
    }
}
