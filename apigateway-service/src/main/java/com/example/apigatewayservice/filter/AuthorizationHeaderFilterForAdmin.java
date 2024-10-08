package com.example.apigatewayservice.filter;

import com.example.apigatewayservice.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationHeaderFilterForAdmin extends AbstractGatewayFilterFactory<AuthorizationHeaderFilterForAdmin.Config> {

    public static class Config {}

    @Value("${jwt.secret.key}")
    private String secretKey;

    public AuthorizationHeaderFilterForAdmin() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (isAuthorizationHeaderTokenMissing(request)) {
                return onError(exchange, "토큰이 존재하지 않습니다", HttpStatus.UNAUTHORIZED);
            }

            Token accessToken = getAccessToken(request);

            try {
                SecretKey signature = createSignature();

                if(!accessToken.isAdminCheck(signature)) {
                    return onError(exchange, "권한이 없습니다", HttpStatus.FORBIDDEN);
                }

                accessToken.validationToken(signature);

                String memberEmail = accessToken.getMemberEmail(signature);

                ServerHttpRequest newRequest = getNewRequest(request, memberEmail);

                return chain.filter(exchange.mutate().request(newRequest).build());
            } catch (RuntimeException e) {
                return onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }));
    }

    private boolean isAuthorizationHeaderTokenMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private ServerHttpRequest getNewRequest(ServerHttpRequest request, String memberEmail) {
        return request.mutate().header("memberEmail", memberEmail).build();
    }

    private Token getAccessToken(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        return Token.replaceHeaderTargetToken(authorizationHeader);
    }

    private SecretKey createSignature() {
        byte[] decode = Base64.getDecoder().decode(secretKey.getBytes());
        return Keys.hmacShaKeyFor(decode);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ObjectMapper om = new ObjectMapper();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        Map<String, Object> errorResponseBody = new HashMap<>();
        errorResponseBody.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        errorResponseBody.put("error", err);

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            DataBuffer buffer = response.bufferFactory().wrap(om.writeValueAsBytes(errorResponseBody));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("에러 문구 구성 중 오류가 있습니다");
        }
    }
}

