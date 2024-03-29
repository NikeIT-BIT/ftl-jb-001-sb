package com.ntarasov.blog.photo.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.photo.api.request.PhotoRequest;
import com.ntarasov.blog.photo.api.request.PhotoSearchRequest;
import com.ntarasov.blog.photo.api.response.PhotoResponse;
import com.ntarasov.blog.photo.exception.PhotoExistException;
import com.ntarasov.blog.photo.exception.PhotoNotExistException;
import com.ntarasov.blog.photo.mapping.PhotoMapping;
import com.ntarasov.blog.photo.routers.PhotoApiRoutes;
import com.ntarasov.blog.photo.service.PhotoApiService;
import com.ntarasov.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Photo API")
public class PhotoApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final PhotoApiService photoApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(PhotoApiRoutes.BY_ID)
    @ApiOperation(value = "Find photo by id", notes = "Photo this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Photo not found")
    })

    public OkResponse<PhotoResponse> byId(
    @ApiParam(value = "Photo id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(PhotoMapping.getInstance().getResponse().convert(
            photoApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }


//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(PhotoApiRoutes.ROOT)
   @ApiOperation(value = "Search photo", notes = "Photo this when you need find photo by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<PhotoResponse>> search(
           @ModelAttribute PhotoSearchRequest request
           ){
        return OkResponse.of(PhotoMapping.getInstance().getSearch().convert(
                photoApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(PhotoApiRoutes.BY_ID)
   @ApiOperation(value = "Update photo", notes = "Photo this when you need update photo info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Photo ID invalid")
   })

    public OkResponse<PhotoResponse> photo(
            @ApiParam(value = "Photo id") @PathVariable String id,
            @RequestBody PhotoRequest photoRequest
   ) throws PhotoNotExistException {
        return OkResponse.of(PhotoMapping.getInstance().getResponse().convert(
                photoApiService.update(photoRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(PhotoApiRoutes.BY_ID)
   @ApiOperation(value = "Delete photo", notes = "User this when you need delete photo")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        photoApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
