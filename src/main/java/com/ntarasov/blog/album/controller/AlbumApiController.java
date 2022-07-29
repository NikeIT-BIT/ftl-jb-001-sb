package com.ntarasov.blog.album.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.album.api.request.AlbumRequest;
import com.ntarasov.blog.album.api.response.AlbumResponse;
import com.ntarasov.blog.album.exception.AlbumExistException;
import com.ntarasov.blog.album.exception.AlbumNotExistException;
import com.ntarasov.blog.album.mapping.AlbumMapping;
import com.ntarasov.blog.album.routers.AlbumApiRoutes;
import com.ntarasov.blog.album.service.AlbumApiService;
import com.ntarasov.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Album API")
public class AlbumApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final AlbumApiService albumApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(AlbumApiRoutes.BY_ID)
    @ApiOperation(value = "Find album by id", notes = "Album this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Album not found")
    })

    public OkResponse<AlbumResponse> byId(
    @ApiParam(value = "Album id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(AlbumMapping.getInstance().getResponse().convert(
            albumApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(AlbumApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Album this when you need create and new create album")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Album alreade exist")
    })

    public OkResponse<AlbumResponse> create(@RequestBody AlbumRequest request) throws AlbumExistException, UserNotExistException {
        return OkResponse.of(AlbumMapping.getInstance().getResponse().convert(albumApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(AlbumApiRoutes.ROOT)
   @ApiOperation(value = "Search album", notes = "Album this when you need find album by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<AlbumResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(AlbumMapping.getInstance().getSearch().convert(
                albumApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(AlbumApiRoutes.BY_ID)
   @ApiOperation(value = "Update album", notes = "Album this when you need update album info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Album ID invalid")
   })

    public OkResponse<AlbumResponse> album(
            @ApiParam(value = "Album id") @PathVariable String id,
            @RequestBody AlbumRequest albumRequest
   ) throws AlbumNotExistException {
        return OkResponse.of(AlbumMapping.getInstance().getResponse().convert(
                albumApiService.update(albumRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(AlbumApiRoutes.BY_ID)
   @ApiOperation(value = "Delete album", notes = "User this when you need delete album")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        albumApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
