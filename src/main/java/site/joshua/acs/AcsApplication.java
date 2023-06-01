package site.joshua.acs;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.TimeZone;

@SpringBootApplication
public class AcsApplication {

	// EC2 에서 시간을 계속 UTC 로 표시하기에 타임존을 한국 시간 기준으로 다시 설정.
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(AcsApplication.class, args);
	}

}
