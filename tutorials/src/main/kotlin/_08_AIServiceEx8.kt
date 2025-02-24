import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.UserMessage

interface AssistantEx8 {
    fun chat(@MemoryId id: Int, @UserMessage message: String): String
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant =
        AiServices.builder(AssistantEx8::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { value -> MessageWindowChatMemory.withMaxMessages(10) }
            .build()

    println(assistant.chat(1, "Hello! My name is Klaus."))
    println(assistant.chat(2, "Hello! My name is Katherin."))

    println(assistant.chat(1, "What is my name?"))
    println(assistant.chat(2, "What is my name?"))
}
