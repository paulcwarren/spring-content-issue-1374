package gettingstarted;

import com.github.paulcwarren.ginkgo4j.Ginkgo4jSpringRunner;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.content.commons.property.PropertyPath;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static com.github.paulcwarren.ginkgo4j.Ginkgo4jDSL.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig.config;

@RunWith(Ginkgo4jSpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GettingStartedTest {

    @Autowired
    private FileRepository fileRepo;
    @Autowired
    private FileContentStore fileContentStore;

    @Autowired
    private WebApplicationContext context;

    private File file;

    {
        Describe("PreAuthorize tests", () -> {
            BeforeEach(() -> {
                RestAssuredMockMvc.webAppContextSetup(context);
            });

            Context("given a file entity with repository and stores with PreAuthorize annotations", () -> {
                BeforeEach(() -> {
                    file = new File();
                    file.setContentMimeType("text/plain");
                    file.setSummary("test file summary");
                    file = fileRepo.save(file);

                    fileContentStore.setContent(file, PropertyPath.from("content"), new ByteArrayInputStream("foo".getBytes()));
                    file = fileRepo.save(file);
                });

                It("should do something...but what?", () -> {

                    given()
                            .auth().with(SecurityMockMvcRequestPostProcessors.user("paul").password("warren").authorities(new SimpleGrantedAuthority("ROLE_READER"), new SimpleGrantedAuthority("ROLE_AUTHOR")))
                            .header("accept", "application/hal+json")
                            .when()
                            .get("/files/" + file.getId())
                            .then()
                            .statusCode(HttpStatus.SC_OK);

//                    given()
//                            .auth().with(SecurityMockMvcRequestPostProcessors.user("paul").password("warren").authorities(new SimpleGrantedAuthority("ROLE_READER"), new SimpleGrantedAuthority("ROLE_AUTHOR")))
//                            .header("accept", "text/plain")
//                            .when()
//                            .get("/files/" + file.getId() + "/content")
//                            .then()
//                            .statusCode(HttpStatus.SC_OK);

                    given()
                            .auth().with(SecurityMockMvcRequestPostProcessors.user("paul").password("warren").authorities(new SimpleGrantedAuthority("ROLE_READER"), new SimpleGrantedAuthority("ROLE_AUTHOR")))
                            .header("content-type", "text/plain")
                            .body("Hello Spring Content with RBAC")
                            .when()
                            .put("/files/" + file.getId() + "/content")
                            .then()
                            .statusCode(HttpStatus.SC_OK);
                });
            });
        });
    }

    @Test
    public void noop() {
    }
}
