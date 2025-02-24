import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.service.AiServices

interface AssistantEx7 {
    fun chat(message: String): String
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant =
        AiServices.builder(AssistantEx7::class.java)
            .chatLanguageModel(model)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .build()

    val answer = assistant.chat("Hello! My name is Klaus.")
    println(answer)

    val answer2 = assistant.chat("What is my name?")
    println(answer2)
}
