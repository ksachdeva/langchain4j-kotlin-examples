import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.UserMessage

enum class Sentiment {
    POSITIVE,
    NEUTRAL,
    NEGATIVE
}

interface SentimentAnalyzer {
    @UserMessage("Analyze sentiment of {{it}}") fun analyzeSentimentOf(text: String): Sentiment

    @UserMessage("Does {{it}} have a positive sentiment?") fun isPositive(text: String): Boolean
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant = AiServices.create(SentimentAnalyzer::class.java, model)

    val sentiment = assistant.analyzeSentimentOf("It is good!")
    println(sentiment)

    val positive = assistant.isPositive("It is bad!")
    println(positive)
}
