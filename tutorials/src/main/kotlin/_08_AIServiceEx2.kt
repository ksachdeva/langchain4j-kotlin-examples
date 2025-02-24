import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.SystemMessage

interface Chef {
    @SystemMessage("You are a professional chef. You are friendly, polite and concise.")
    fun answer(question: String): String
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant = AiServices.create(Chef::class.java, model)

    val userMessage = "How long should I grill chicken?"

    val answer = assistant.answer(userMessage)

    println(answer)
}
