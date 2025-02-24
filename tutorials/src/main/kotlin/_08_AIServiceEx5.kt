import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.model.output.structured.Description
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.UserMessage

enum class IssueCategory {
    @Description(
        "The feedback mentions issues with the hotel's maintenance, such as air conditioning and plumbing problems"
    )
    MAINTENANCE_ISSUE,
    @Description(
        "The feedback mentions issues with the service provided, such as slow room service"
    )
    SERVICE_ISSUE,
    @Description(
        "The feedback mentions issues affecting the comfort of the stay, such as uncomfortable room conditions"
    )
    COMFORT_ISSUE,
    @Description(
        "The feedback mentions issues with hotel facilities, such as problems with the bathroom plumbing"
    )
    FACILITY_ISSUE,
    @Description(
        "The feedback mentions issues with the cleanliness of the hotel, such as dust and stains"
    )
    CLEANLINESS_ISSUE,
    @Description(
        "The feedback mentions issues with internet connectivity, such as unreliable Wi-Fi"
    )
    CONNECTIVITY_ISSUE,
    @Description(
        "The feedback mentions issues with the check-in process, such as it being tedious and time-consuming"
    )
    CHECK_IN_ISSUE,
    @Description(
        "The feedback mentions a general dissatisfaction with the overall hotel experience due to multiple issues"
    )
    OVERALL_EXPERIENCE_ISSUE
}

interface HotelReviewIssueAnalyzer {
    @UserMessage("Please analyse the following review: |||{{it}}|||")
    fun analyzeReview(review: String): List<IssueCategory>
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val assistant = AiServices.create(HotelReviewIssueAnalyzer::class.java, model)

    val review: String =
        "Our stay at hotel was a mixed experience. The location was perfect, just a stone's throw away " +
            "from the beach, which made our daily outings very convenient. The rooms were spacious and well-decorated, " +
            "providing a comfortable and pleasant environment. However, we encountered several issues during our " +
            "stay. The air conditioning in our room was not functioning properly, making the nights quite uncomfortable. " +
            "Additionally, the room service was slow, and we had to call multiple times to get extra towels. Despite the " +
            "friendly staff and enjoyable breakfast buffet, these issues significantly impacted our stay."

    val issueCategories = assistant.analyzeReview(review)
    println(issueCategories)
}
