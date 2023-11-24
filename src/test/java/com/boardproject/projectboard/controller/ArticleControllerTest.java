package com.boardproject.projectboard.controller;

import com.boardproject.projectboard.config.SecurityConfig;
import com.boardproject.projectboard.dto.ArticleWithCommentsDto;
import com.boardproject.projectboard.dto.UserAccountDto;
import com.boardproject.projectboard.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View controller")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

//    @Disabled("developing")
    @DisplayName("[view][GET] board detail list page- correct call")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
//        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
//                .andExpect(model().attributeExists("paginationBarNumbers"))
//                .andExpect(model().attributeExists("searchTypes"))
//                .andExpect(model().attribute("searchTypeHashtag", SearchType.HASHTAG));
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
//        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

//    @Disabled("developing")
    @DisplayName("[view][GET] board detail list page- correct call")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticleView() throws Exception {
        // Given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        // When & Then
        mvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
        then(articleService).should().getArticle(articleId);
    }

    @Disabled("developing")
    @DisplayName("[view][GET] article list - correct call")
    @Test
    void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // Given
//        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
//        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles/search"));
//                .andExpect(model().attributeExists("paginationBarNumbers"))
//                .andExpect(model().attributeExists("searchTypes"))
//                .andExpect(model().attribute("searchTypeHashtag", SearchType.HASHTAG));
//        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
//        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @Disabled("developing")
    @DisplayName("[view][GET] board hashtag search page - correct call")
    @Test
    void givenNothing_whenRequestingArticleSearchHashtagView_thenReturnsArticleSearchHashtagView() throws Exception {
        // Given
//        List<String> hashtags = List.of("#java", "#spring", "#boot");
//        given(articleService.searchArticlesViaHashtag(eq(null), any(Pageable.class))).willReturn(Page.empty());
//        given(articleService.getHashtags()).willReturn(hashtags);
//        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(1, 2, 3, 4, 5));

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));
//                .andExpect(model().attribute("articles", Page.empty()))
//                .andExpect(model().attribute("hashtags", hashtags))
//                .andExpect(model().attributeExists("paginationBarNumbers"))
//                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
//        then(articleService).should().searchArticlesViaHashtag(eq(null), any(Pageable.class));
//        then(articleService).should().getHashtags();
//        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }


    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "uno",
                "pw",
                "uno@email.com",
                "memo",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }
}