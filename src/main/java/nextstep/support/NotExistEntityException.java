package nextstep.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException() {
        super("대상을 찾을 수 없습니다.");
    }
}
