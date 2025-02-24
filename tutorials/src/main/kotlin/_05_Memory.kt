import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.memory.chat.TokenWindowChatMemory
import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.model.openai.OpenAiStreamingChatModel
import dev.langchain4j.model.openai.OpenAiTokenizer
import java.util.concurrent.CompletableFuture

fun main() {
    val model =
        OpenAiStreamingChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val chatMemory = TokenWindowChatMemory.withMaxTokens(1000, OpenAiTokenizer())

    val systemMessage =
        SystemMessage.from(
            "You are a senior developer explaining to another senior developer, " +
                "the project you are working on is an e-commerce platform with Java back-end, " +
                "Oracle database,and Spring Data JPA"
        )
    chatMemory.add(systemMessage)

    val userMessage1 =
        UserMessage.userMessage(
            "How do I optimize database queries for a large-scale e-commerce platform? " +
                "Answer short in three to five lines maximum."
        )
    chatMemory.add(userMessage1)

    println("[User]: " + userMessage1.text())
    print("[LLM]: ")

    val futureAiMessage = CompletableFuture<AiMessage>()

    val responseHandler =
        object : StreamingChatResponseHandler {
            override fun onPartialResponse(message: String) {
                println("Received message: $message")
            }

            override fun onCompleteResponse(response: ChatResponse) {
                println("\n\nDone streaming")
                futureAiMessage.complete(response.aiMessage())
            }

            override fun onError(throwable: Throwable) {
                println("Error: ${throwable.message}")
            }
        }

    model.chat(chatMemory.messages(), responseHandler)
    chatMemory.add(futureAiMessage.get())

    val userMessage2 =
        UserMessage.userMessage(
            "Give a concrete example implementation of the first point? " +
                "Be short, 10 lines of code maximum."
        )
    chatMemory.add(userMessage2)

    println("\n\n[User]: " + userMessage2.text())
    print("[LLM]: ")

    model.chat(chatMemory.messages(), responseHandler)
}
