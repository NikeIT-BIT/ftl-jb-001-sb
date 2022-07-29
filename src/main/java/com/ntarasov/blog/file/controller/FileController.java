package com.ntarasov.blog.file.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.file.api.response.FileResponse;
import com.ntarasov.blog.file.exception.FileExistException;
import com.ntarasov.blog.file.exception.FileNotExistException;
import com.ntarasov.blog.file.mapping.FileMapping;
import com.ntarasov.blog.file.model.FileDoc;
import com.ntarasov.blog.file.routers.FileApiRoutes;
import com.ntarasov.blog.file.service.FileApiService;
import com.ntarasov.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(value = "File API")
public class FileController {
//<---------------------------------FINAL------------------------------------------------->
    private final FileApiService fileApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(FileApiRoutes.DOWNLOAD)
    @ApiOperation(value = "Find file by id", notes = "File this when you need full info about")

    public  void byId(
    @ApiParam(value = "File id") @PathVariable ObjectId id, HttpServletResponse response
            ) throws ChangeSetPersister.NotFoundException, IOException {
        FileDoc fileDoc = fileApiService.findById(id).orElseThrow();


        response.addHeader("Content-Type", fileDoc.getContentType());
        response.addHeader("Content-Disposition", "attachment; filename=\""+fileDoc.getTitle()+"\"");
        FileCopyUtils.copy(fileApiService.downloadById(id),response.getOutputStream());

    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(FileApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "File this when you need create and new create file")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "File alreade exist")
    })

    public @ResponseBody OkResponse<FileResponse> create(
            @RequestParam MultipartFile multipartFile,
            @RequestParam ObjectId ownerId
    ) throws FileExistException, IOException, UserNotExistException {
        return OkResponse.of(FileMapping.getInstance().getResponse().convert(fileApiService.create(multipartFile, ownerId)));
    }
}
