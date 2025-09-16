package chatGPT;

import okhttp3.*;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class queryGPTo1 {
    static long startTime;
    static long endTime;
    static class ChatMessage {
        String role;
        String content;

        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    static class ChatRequest {
        String model;
        List<ChatMessage> messages;

        @SerializedName("max_completion_tokens")
        int maxTokens;


        public ChatRequest(String model, List<ChatMessage> messages, int maxTokens) {
            this.model = model;
            this.messages = messages;
            this.maxTokens = maxTokens;
        }
    }

    static class ChatCompletionResponse {
        List<Choice> choices;

        static class Choice {
            Message message;

            static class Message {
                String role;
                String content;
            }
        }
    }


    public static void ask(String API_ENDPOINT, String API_KEY, String problem, String answerPrompt) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();

        ChatMessage userMessage = new ChatMessage("user", problem);

        ChatRequest chatRequest = new ChatRequest(
                "o1",
                Collections.singletonList(userMessage),
                4096
        );

        String jsonRequestBody = gson.toJson(chatRequest);

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonRequestBody
        );

        Request request = new Request.Builder()
                .url(API_ENDPOINT)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.out.println("Error: " + response.code());
                    System.out.println("Info: " + response.body().string());
                    return;
                }

                // 读取响应体并解析
                String responseBody = response.body().string();
                ChatCompletionResponse completionResponse = gson.fromJson(responseBody, ChatCompletionResponse.class);

                // 解析生成的答案
                if (completionResponse != null
                        && completionResponse.choices != null
                        && !completionResponse.choices.isEmpty()) {
                    String reply = completionResponse.choices.get(0).message.content;
                    System.out.println(answerPrompt + reply);
                } else {
                    System.out.println("No response.");
                }
            }
        });
    }
}
