package site.joshua.acs.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.joshua.acs.service.MinutesFileService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MinutesController {

    private final MinutesFileService minutesFileService;

    @GetMapping("/minutes")
    public String minutes() {

        return "minutes/minutes";
    }

    @PostMapping("/minutes/new")
    public String uploadFile(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

        log.info("request={}", request);
        log.info("multipartFile={}", file);

        minutesFileService.saveUploadFile(file);

        return "redirect:/minutes";
    }

}
