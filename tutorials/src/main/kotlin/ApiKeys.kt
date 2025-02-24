import dev.langchain4j.internal.Utils.getOrDefault

object ApiKeys {
    val OPENAI_API_KEY: String
    init { 
        OPENAI_API_KEY = getOrDefault(System.getenv("OPENAI_API_KEY"), "demo")        
    }
}