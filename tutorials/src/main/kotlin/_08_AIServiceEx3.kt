import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V

interface TextUtils {
    @SystemMessage("You are a professional translator into {{language}}")
    @UserMessage("Translate the following text: {{text}}")
    fun translate(@V("text") text: String, @V("language") language: String): String

    @SystemMessage(
        "Summarize every message from user in {{n}} bullet points. Provide only bullet points."
    )
    fun summarize(@UserMessage text: String, @V("n") n: Int): List<String>
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant = AiServices.create(TextUtils::class.java, model)

    val translation = assistant.translate("Hello, how are you?", "italian")
    println(translation)

    val text: String =
        """
        AI, or artificial intelligence, is a branch of computer science that aims to create 
        machines that mimic human intelligence. This can range from simple tasks such as recognizing 
        patterns or speech to more complex tasks like making decisions or predictions.
    """

    val bulletPoints = assistant.summarize(text, 3)

    for (point in bulletPoints) {
        println("$point")
    }
}
