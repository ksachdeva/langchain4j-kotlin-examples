import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.model.openai.OpenAiStreamingChatModel

fun main() {
    val model =
        OpenAiStreamingChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val prompt = "Write a short funny poem about developers & null-pointers, 10 lines maximum."

    val responseHandler =
        object : StreamingChatResponseHandler {
            override fun onPartialResponse(message: String) {
                println("Received message: $message")
            }

            override fun onCompleteResponse(message: ChatResponse) {
                println("\n\nDone streaming")
            }

            override fun onError(throwable: Throwable) {
                println("Error: ${throwable.message}")
            }
        }

    model.chat(prompt, responseHandler)
}
