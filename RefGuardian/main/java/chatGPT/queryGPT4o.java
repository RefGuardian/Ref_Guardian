package chatGPT;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;

import java.util.Arrays;

public class queryGPT4o {
    public static String query(String apiKey, String apiUrl, String question) throws InterruptedException {
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(apiKey)
                .apiHost(apiUrl)
                .build()
                .init();
        Message message = Message.of(question);
        ChatCompletion chatCompletion = ChatCompletion.builder().model("gpt-4o-2024-11-20").
                messages(Arrays.asList(message)).maxTokens(4096).temperature(0.1).build();
        try {
            ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
            Message res = response.getChoices().get(0).getMessage();
            return res.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(1000);
            return query(apiKey, apiUrl, question);
        }
    }
}
