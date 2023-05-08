package site.joshua.acs.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


public enum Gender {
    MAN("남자"), WOMAN("여자");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
