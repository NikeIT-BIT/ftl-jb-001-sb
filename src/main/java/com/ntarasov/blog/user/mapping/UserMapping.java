package com.ntarasov.blog.user.mapping;

import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.base.mapping.BaseMapping;
import com.ntarasov.blog.user.api.request.UpdateUserRequest;
import com.ntarasov.blog.user.api.response.UserFullResponse;
import com.ntarasov.blog.user.api.response.UserResponse;
import com.ntarasov.blog.user.model.UserDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class UserMapping {
    public static class RequestMapping extends BaseMapping<UpdateUserRequest, UserDoc> {

        @Override
        public UserDoc convert(UpdateUserRequest updateUserRequest) {
            return UserDoc.builder()
                    .id(updateUserRequest.getId())
                    .firstName(updateUserRequest.getFirstName())
                    .lastName(updateUserRequest.getLastName())
                    .email(updateUserRequest.getEmail())
                    .build();
        }


        @Override
        public UpdateUserRequest unMapping(UserDoc userDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseMapping extends BaseMapping<UserDoc, UserResponse> {

        @Override
        public UserResponse convert(UserDoc userDoc) {
            return UserResponse.builder()
                    .id(userDoc.getId().toString())
                    .firstName(userDoc.getFirstName())
                    .lastName(userDoc.getLastName())
                    .email(userDoc.getEmail())
                    .build();
        }


        @Override
        public UserDoc unMapping(UserResponse userResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseFullMapping extends BaseMapping<UserDoc, UserFullResponse> {

        @Override
        public UserFullResponse convert(UserDoc userDoc) {
            return UserFullResponse.builder()
                    .id(userDoc.getId().toString())
                    .firstName(userDoc.getFirstName())
                    .lastName(userDoc.getLastName())
                    .email(userDoc.getEmail())
                    .address(userDoc.getAddress())
                    .company(userDoc.getCompany())
                    .build();
        }


        @Override
        public UserDoc unMapping(UserFullResponse response) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class SearchMapping extends BaseMapping<SearchResponse<UserDoc>, SearchResponse<UserResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<UserResponse> convert(SearchResponse<UserDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<UserDoc> unMapping(SearchResponse<UserResponse> userResponses) {
            throw new RuntimeException("dont use this");
        }
    }

    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final ResponseFullMapping responseFull = new ResponseFullMapping();
    private final SearchMapping search = new SearchMapping();


    public static UserMapping getInstance() {
        return new UserMapping();
    }

}
