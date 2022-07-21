package com.ntarasov.blog.file.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.file.api.response.FileResponse;
import com.ntarasov.blog.file.exception.FileExistException;
import com.ntarasov.blog.file.exception.FileNotExistException;
import com.ntarasov.blog.file.mapping.FileMapping;
import com.ntarasov.blog.file.routers.FileApiRoutes;
import com.ntarasov.blog.file.service.FileApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "File API")
public class FileApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final FileApiService fileApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(FileApiRoutes.BY_ID)
    @ApiOperation(value = "Find file by id", notes = "File this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "File not found")
    })

    public OkResponse<FileResponse> byId(
    @ApiParam(value = "File id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(FileMapping.getInstance().getResponse().convert(
            fileApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }


//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(FileApiRoutes.ROOT)
   @ApiOperation(value = "Search file", notes = "File this when you need find file by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<FileResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(FileMapping.getInstance().getSearch().convert(
                fileApiService.search(request)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(FileApiRoutes.BY_ID)
   @ApiOperation(value = "Delete file", notes = "User this when you need delete file")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        fileApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
