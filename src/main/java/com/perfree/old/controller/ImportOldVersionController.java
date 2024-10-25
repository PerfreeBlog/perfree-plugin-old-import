package com.perfree.old.controller;

import com.perfree.commons.common.CommonResult;
import com.perfree.old.controller.vo.OldVersionImportReqVO;
import com.perfree.old.service.ImportOldVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.perfree.commons.common.CommonResult.success;


@RestController
@Tag(name = "旧版本数据导入")
@RequestMapping("api/auth/plugin/oldImport")
public class ImportOldVersionController {

    @Resource
    private ImportOldVersionService importOldVersionService;

    @PostMapping("/oldVersionImport")
    @Operation(summary = "旧版本数据导入")
    public CommonResult<Boolean> oldVersionImport(@RequestBody OldVersionImportReqVO reqVO) {
        return success(importOldVersionService.oldVersionImport(reqVO));
    }
}
