package pl.lbu.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lbu.rest.data.RepositoryData;
import pl.lbu.rest.properties.GlobalProperties;
import pl.lbu.rest.properties.RepositoryProperties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginTests {

    @Autowired
    private GlobalProperties globalProperties;

    @Autowired
    private RepositoryProperties repositoryProperties;


    @BeforeEach
    void setRestAssured() {
        RestAssured.baseURI = globalProperties.getDomainUrl();
    }

    @Test
    @DisplayName("repo")
    void repo() {
        Response response = when().request("GET", repositoryProperties.getSummaryRepoUrl());
        response.then().statusCode(200);
        RepositoryData[] repositoryData = response.getBody().as(RepositoryData[].class);
        assertNotNull(repositoryData[0].getId());
    }

    @Test
    @DisplayName("login_basic")
    void login_basic() {
        given().auth()
                .basic(globalProperties.getUsername(), globalProperties.getPassword())
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("login_basic")
    void login_basic_bad() {
        given().auth()
                .basic(globalProperties.getUsername(), "")
                .when()
                .post(globalProperties.getDomainUrl())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
