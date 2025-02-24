import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName

fun main() {
    val model = OpenAiChatModel.builder()
                               .apiKey(ApiKeys.OPENAI_API_KEY)
                               .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                               .build()

    val answer = model.chat("What is the meaning of life in 5 lines?")
    println(answer)
}