package com.ntarasov.blog.comment.mapping;

import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.base.mapping.BaseMapping;
import com.ntarasov.blog.comment.api.request.CommentRequest;
import com.ntarasov.blog.comment.api.response.CommentResponse;
import com.ntarasov.blog.comment.model.CommentDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class CommentMapping {
    public static class RequestMapping extends BaseMapping<CommentRequest, CommentDoc> {

        @Override
        public CommentDoc convert(CommentRequest commentRequest) {
            return CommentDoc.builder()
                    .id(commentRequest.getId())
                    .articleId(commentRequest.getArticleId())
                    .userId(commentRequest.getUserId())
                    .messege(commentRequest.getMessege())
                    .build();
        }


        @Override
        public CommentRequest unMapping(CommentDoc commentDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseMapping extends BaseMapping<CommentDoc, CommentResponse> {

        @Override
        public CommentResponse convert(CommentDoc commentDoc) {
            return CommentResponse.builder()
                    .id(commentDoc.getId().toString())
                    .articleId(commentDoc.getArticleId().toString())
                    .userId(commentDoc.getUserId().toString())
                    .messege(commentDoc.getMessege())
                    .build();
        }


        @Override
        public CommentDoc unMapping(CommentResponse commentResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class SearchMapping extends BaseMapping<SearchResponse<CommentDoc>, SearchResponse<CommentResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<CommentResponse> convert(SearchResponse<CommentDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<CommentDoc> unMapping(SearchResponse<CommentResponse> commentResponses) {
            throw new RuntimeException("dont use this");
        }
    }

    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static CommentMapping getInstance() {
        return new CommentMapping();
    }

}
