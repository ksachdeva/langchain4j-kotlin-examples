import dev.langchain4j.model.input.structured.StructuredPrompt
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.model.output.structured.Description
import dev.langchain4j.service.AiServices

data class Recipe(
    @Description("short title, 3 words maximum") val title: String,
    @Description("short description, 2 sentences maximum") val description: String,
    @Description(
        "each step should be described in 6 to 8 words, steps should rhyme with each other"
    )
    val steps: List<String>,
    private val preparationTimeMinutes: Int
)

@StructuredPrompt("Create a recipe of a {{dish}} that can be prepared using only {{ingredients}}")
class CreateRecipePrompt(private val dish: String, private val ingredients: List<String>)

interface ChefEx6 {
    fun createRecipeFrom(vararg ingredients: String): Recipe

    fun createRecipe(prompt: CreateRecipePrompt): Recipe
}

fun main() {
    val model =
        OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .responseFormat("json_schema")
            .strictJsonSchema(true)
            .build()

    val assistant = AiServices.create(ChefEx6::class.java, model)

    val recipe =
        assistant.createRecipeFrom("cucumber", "tomato", "feta", "onion", "olives", "lemon")
    println(recipe)

    val prompt =
        CreateRecipePrompt(
            "oven dish",
            listOf("cucumber", "tomato", "feta", "onion", "olives", "potatoes")
        )

    val recipe2 = assistant.createRecipe(prompt)
    print(recipe2)
}
