package com.ntarasov.blog.file.mapping;

import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.base.mapping.BaseMapping;
import com.ntarasov.blog.file.api.response.FileResponse;
import com.ntarasov.blog.file.model.FileDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class FileMapping {



    public static class ResponseMapping extends BaseMapping<FileDoc, FileResponse> {

        @Override
        public FileResponse convert(FileDoc fileDoc) {
            return FileResponse.builder()
                    .id(fileDoc.getId().toString())
                    .title(fileDoc.getTitle())
                    .ownerId(fileDoc.getOwnerId().toString())
                    .build();
        }

        @Override
        public FileDoc unMapping(FileResponse fileResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<FileDoc>, SearchResponse<FileResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<FileResponse> convert(SearchResponse<FileDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<FileDoc> unMapping(SearchResponse<FileResponse> fileResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static FileMapping getInstance() {
        return new FileMapping();
    }

}
