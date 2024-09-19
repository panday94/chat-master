package com.master.chat.llm.locallm.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class BaseChatCompletion implements Serializable {

    private Boolean stream = false;

}


