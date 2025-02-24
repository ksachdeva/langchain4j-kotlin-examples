import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.input.PromptTemplate
import dev.langchain4j.model.input.structured.StructuredPrompt
import dev.langchain4j.model.input.structured.StructuredPromptProcessor
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName

fun simplePromptExample(model: ChatLanguageModel) {
    val template =
        "Create a recipe for a {{dishType}} with the following ingredients: {{ingredients}}"
    val promptTemplate = PromptTemplate.from(template)
    val variables =
        mapOf("dishType" to "oven dish", "ingredients" to "potatoes, onions, garlic, olive oil")
    val prompt = promptTemplate.apply(variables)

    val response = model.chat(prompt.text())

    println(response)
}

fun structuredPromptExample(model: ChatLanguageModel) {
    @StructuredPrompt(
        value =
            [
                "Create a recipe of a {{dish}} that can be prepared using only {{ingredients}}.",
                "Structure your answer in the following way:",
                "Recipe name: ...",
                "Description: ...",
                "Preparation time: ...",
                "Required ingredients:",
                "- ...",
                "- ...",
                "Instructions:",
                "- ...",
                "- ..."
            ]
    )
    class CreateRecipePrompt(val dish: String, val ingredients: List<String>) {}

    val receipePrompt =
        CreateRecipePrompt("salad", listOf("cucumber", "tomato", "feta", "onion", "olives"))
    println(receipePrompt)
    println(receipePrompt.dish)
    println(receipePrompt.ingredients)
    val prompt = StructuredPromptProcessor.toPrompt(receipePrompt)

    println(prompt.text())

    val response = model.chat(prompt.text())

    println(response)
}

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Incorrect usage; please provide an example type (simple or structured)")
        println("Usage: _03_PromptTemplate simple|structured")
        return
    }

    // create the chat model
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val exampleType = args[0]

    if (exampleType == "simple") {
        simplePromptExample(model)
        return
    }

    structuredPromptExample(model)
}
