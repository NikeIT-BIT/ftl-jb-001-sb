package com.ntarasov.blog.todoTask.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.todoTask.api.request.TodoTaskRequest;
import com.ntarasov.blog.todoTask.api.request.TodoTaskSearchRequest;
import com.ntarasov.blog.todoTask.api.response.TodoTaskResponse;
import com.ntarasov.blog.todoTask.exception.TodoTaskExistException;
import com.ntarasov.blog.todoTask.exception.TodoTaskNotExistException;
import com.ntarasov.blog.todoTask.mapping.TodoTaskMapping;
import com.ntarasov.blog.todoTask.routers.TodoTaskApiRoutes;
import com.ntarasov.blog.todoTask.service.TodoTaskApiService;
import com.ntarasov.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Api(value = "TodoTask API")
public class TodoTaskApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final TodoTaskApiService todoTaskApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(TodoTaskApiRoutes.BY_ID)
    @ApiOperation(value = "Find todoTask by id", notes = "TodoTask this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "TodoTask not found")
    })

    public OkResponse<TodoTaskResponse> byId(
    @ApiParam(value = "TodoTask id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(TodoTaskMapping.getInstance().getResponse().convert(
            todoTaskApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(TodoTaskApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "TodoTask this when you need create and new create todoTask")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "TodoTask alreade exist")
    })

    public OkResponse<TodoTaskResponse> create(@RequestBody TodoTaskRequest request) throws TodoTaskExistException, UserNotExistException {
        return OkResponse.of(TodoTaskMapping.getInstance().getResponse().convert(todoTaskApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(TodoTaskApiRoutes.ROOT)
   @ApiOperation(value = "Search todoTask", notes = "TodoTask this when you need find todoTask by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<TodoTaskResponse>> search(
           @ModelAttribute TodoTaskSearchRequest request
           ) throws ResponseStatusException {
        return OkResponse.of(TodoTaskMapping.getInstance().getSearch().convert(
                todoTaskApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(TodoTaskApiRoutes.BY_ID)
   @ApiOperation(value = "Update todoTask", notes = "TodoTask this when you need update todoTask info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "TodoTask ID invalid")
   })

    public OkResponse<TodoTaskResponse> todoTask(
            @ApiParam(value = "TodoTask id") @PathVariable String id,
            @RequestBody TodoTaskRequest todoTaskRequest
   ) throws TodoTaskNotExistException {
        return OkResponse.of(TodoTaskMapping.getInstance().getResponse().convert(
                todoTaskApiService.update(todoTaskRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(TodoTaskApiRoutes.BY_ID)
   @ApiOperation(value = "Delete todoTask", notes = "User this when you need delete todoTask")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        todoTaskApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
