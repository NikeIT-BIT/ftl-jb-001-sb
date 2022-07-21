package com.ntarasov.blog.article.mapping;

import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.base.mapping.BaseMapping;
import com.ntarasov.blog.article.api.request.ArticleRequest;
import com.ntarasov.blog.article.api.response.ArticleResponse;
import com.ntarasov.blog.article.model.ArticleDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class ArticleMapping {
    public static class RequestMapping extends BaseMapping<ArticleRequest, ArticleDoc> {

        @Override
        public ArticleDoc convert(ArticleRequest articleRequest) {
            return ArticleDoc.builder()
                    .id(articleRequest.getId())
                    .titel(articleRequest.getTitel())
                    .body(articleRequest.getBody())
                    .ownerld(articleRequest.getOwnerld())
                    .build();
        }


        @Override
        public ArticleRequest unMapping(ArticleDoc articleDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseMapping extends BaseMapping<ArticleDoc, ArticleResponse> {

        @Override
        public ArticleResponse convert(ArticleDoc articleDoc) {
            return ArticleResponse.builder()
                    .id(articleDoc.getId().toString())
                    .titel(articleDoc.getTitel())
                    .body(articleDoc.getBody())
                    .ownerld(articleDoc.getOwnerld() != null ? articleDoc.getOwnerld().toString() : null)
                    .build();
        }


        @Override
        public ArticleDoc unMapping(ArticleResponse articleResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class SearchMapping extends BaseMapping<SearchResponse<ArticleDoc>, SearchResponse<ArticleResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<ArticleResponse> convert(SearchResponse<ArticleDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<ArticleDoc> unMapping(SearchResponse<ArticleResponse> articleResponses) {
            throw new RuntimeException("dont use this");
        }
    }

    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static ArticleMapping getInstance() {
        return new ArticleMapping();
    }

}
