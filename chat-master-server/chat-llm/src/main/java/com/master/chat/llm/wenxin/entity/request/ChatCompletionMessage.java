package com.master.chat.llm.wenxin.entity.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文心一言聊天内容
 *
 * @author: Yang
 * @date: 2023/9/7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChatCompletionMessage implements Serializable {
    @NotNull
    private String role;
    private String content;

    public static Builder builder() {
        return new Builder();
    }

    private ChatCompletionMessage(Builder builder) {
        setRole(builder.role);
        setContent(builder.content);
    }

    @Getter
    @AllArgsConstructor
    public enum Role {
        USER("user"),
        ASSISTANT("assistant");
        private String name;
    }

    public static final class Builder {
        private @NotNull String role;
        private String content;

        public Builder() {
        }

        public Builder role(@NotNull String role) {
            this.role = role;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public ChatCompletionMessage build() {
            return new ChatCompletionMessage(this);
        }
    }

}
