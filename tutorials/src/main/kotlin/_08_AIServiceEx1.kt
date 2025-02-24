import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.service.AiServices

interface Assistant {
    fun chat(question: String): String
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant = AiServices.create(Assistant::class.java, model)

    val userMessage =
        "Translate 'Plus-Values des cessions de valeurs mobilières, de droits sociaux et gains assimilés'"

    val answer = assistant.chat(userMessage)

    println(answer)
}
