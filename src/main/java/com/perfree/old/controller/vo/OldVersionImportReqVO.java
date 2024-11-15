package com.perfree.old.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "旧版本数据导入REQ VO")
@Data
public class OldVersionImportReqVO {

    private String dataBaseIp;

    private String dataBaseName;

    private String dataBasePassword;

    private String dataBasePort;

    private String dataBaseUserName;

}
