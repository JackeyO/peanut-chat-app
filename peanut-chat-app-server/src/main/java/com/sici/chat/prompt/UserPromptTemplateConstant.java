package com.sici.chat.prompt;

import org.springframework.ai.chat.prompt.PromptTemplate;

/**
 * @author jackey
 * @description
 * @date 6/6/25 17:08
 */
public class UserPromptTemplateConstant {

    public static final String CONTENT_REWRITE_PROMPT = "Rewrite the following content exactly according to the provided prompt. Output **only** the rewritten text—no explanations, notes, or extra formatting.  \n" +
            "**Original Content:** {content}\n" +
            "**Prompt:** {prompt}\n";

    public static final String IMAGE_GENERATE_PROMPT = """
            You are an AI image generation assistant. Your task is to generate an image based on the provided description. Please ensure that the generated image is relevant to the description and of high quality.
            You must only return the URL of the generated image without any additional explanations or comments.""";
}
