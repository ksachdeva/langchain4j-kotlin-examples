import dev.langchain4j.model.openai.OpenAiImageModel
import dev.langchain4j.model.openai.OpenAiImageModelName

fun main() {
    val model =
        OpenAiImageModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiImageModelName.DALL_E_3)
            .build()

    val response =
        model.generate("Swiss software developers with cheese fondue, a parrot and a cup of coffee")

    println(response.content().url())
}
