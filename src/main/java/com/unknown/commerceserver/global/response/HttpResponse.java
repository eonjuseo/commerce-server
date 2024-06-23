package com.unknown.commerceserver.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class HttpResponse {
    @Getter
    @RequiredArgsConstructor
    public enum Success {
        // 200
        OK(HttpStatus.OK, "성공"),
        CREATED(HttpStatus.CREATED, "리소스가 생성되었습니다."),
        NO_CONTENT(HttpStatus.NO_CONTENT, "성공하였지만, 리소스가 없습니다."),

        // 300
        MOVED_PERMANENTLY(HttpStatus.MOVED_PERMANENTLY, "영구적으로 이동합니다."),
        FOUND(HttpStatus.FOUND, "다른 URI에서 리소스를 찾았습니다."),
        NOT_MODIFIED(HttpStatus.NOT_MODIFIED, "캐시를 사용하세요."),
        TEMPORARY_REDIRECT(HttpStatus.TEMPORARY_REDIRECT, "다른 URI에서 리소스를 찾았고 본문도 유지됩니다."),
        PERMANENT_REDIRECT(HttpStatus.PERMANENT_REDIRECT, "영구적으로 이동하고 본문도 유지됩니다.");

        private final HttpStatus status;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Fail {
        // 400
        BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),

        // 403
        FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 접근입니다."),

        // 404
        NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
        NOT_FOUND_STOCK_STATUS(HttpStatus.NOT_FOUND, "존재하지 않는 재고 상태입니다."),
        NOT_FOUND_ORDER_STATUS(HttpStatus.NOT_FOUND, "존재하지 않는 주문 상태입니다."),
        NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
        NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
        NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "존재하지 않는 제품입니다."),

        // 405
        METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP METHOD 입니다."),

        // 409
        OUT_OF_ITEM_STOCK(HttpStatus.CONFLICT, "해당 상품의 재고가 없습니다."),

        // 500 서버 에러
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다.");

        private final HttpStatus status;
        private final String message;
    }
}