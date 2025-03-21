package com.master.chat.llm.locallm.dify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.master.chat.llm.locallm.base.entity.BaseChatCompletion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Slf4j
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FileItem {

    /**
     * 支持类型：图片 image（目前仅支持图片格式）
     */
    private String type = "image";

    /**
     * 传递方式
     * remote_url: 图片地址
     * local_file: 上传文件。
     */
    @JsonProperty("transfer_method")
    private String transferMethod = "remote_url";

    /**
     * 图片地址。（仅当传递方式为 remote_url 时）
     */
    private String url;

    /**
     * 上传文件 ID。（仅当传递方式为 local_file 时）。
     */
    @JsonProperty("upload_file_id")
    private String uploadFileId;

}


