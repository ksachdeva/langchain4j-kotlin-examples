import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import java.time.Duration.ofSeconds

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .temperature(0.3)
            .logRequests(true)
            .logResponses(true)
            .timeout(ofSeconds(60))
            .build()

    val answer = model.chat("Explain in three lines how to make a beautiful painting")
    println(answer)
}
