package io.nology.project.common;

import io.nology.project.auth.JwtService;
import io.nology.project.auth.entity.AppUser;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestBuilder {
    private static final Logger log = LogManager.getLogger(RequestBuilder.class);
    private final RequestSpecBuilder requestSpecBuilder;
    private final JwtService jwtService;

    public RequestBuilder(int port, String baseUri, JwtService jwtService) {
        this.requestSpecBuilder = new RequestSpecBuilder().setPort(port).setBaseUri(baseUri)
                .setContentType(ContentType.JSON);
        this.jwtService = jwtService;
    }

    public RequestBuilder withJwt(AppUser user){
        String token = this.jwtService.generateAccessToken(user);
        log.info("Created JWT: {}", token);
        requestSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return this;
    }

    public RequestSpecification build(){
        return RestAssured.given(this.requestSpecBuilder.build());
    }
}
