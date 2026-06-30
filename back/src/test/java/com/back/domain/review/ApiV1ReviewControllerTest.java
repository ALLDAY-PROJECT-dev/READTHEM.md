package com.back.domain.review;

import com.back.domain.review.controller.ApiV1ReviewController;
import com.back.domain.review.entity.Review;
import com.back.domain.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ApiV1ReviewControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 다건 조회")
    @WithUserDetails("user1")
    void t1() throws Exception {

        long bookId = 1L;
        List<Review> reviews = List.of();

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/reviews/book/%d".formatted(bookId)))
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ApiV1ReviewController.class))
                .andExpect(handler().methodName("getReviews"))
                .andExpect(status().isOk());

        for (int i = 0; i < reviews.size(); i++) {
            resultActions
                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(0))
                    .andExpect(jsonPath("$[%d].rating".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].content".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].modifiedDate".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].createdDate".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].reviewer".formatted(i)).exists())
                    .andExpect(jsonPath("$[%d].reviewer.id".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].reviewer.githubId".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].reviewer.githubLink".formatted(i)).value(""))
                    .andExpect(jsonPath("$[%d].tags".formatted(i)).exists());

            // List<String> tags = reviews.get(i).getTags();
            int tagCount = 0; // tags.size();

            for (int j = 0; j < tagCount; j++) {

                resultActions
                        .andExpect(jsonPath("$[%d].tags[%d]".formatted(i, j)).value("tags.get(j)"));

            }
        }
    }

}