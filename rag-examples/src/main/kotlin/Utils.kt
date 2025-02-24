import dev.langchain4j.internal.Utils.getOrDefault
import java.net.URISyntaxException
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.Paths
import java.util.Scanner

object Utils {
    val OPENAI_API_KEY: String

    init {
        OPENAI_API_KEY = getOrDefault(System.getenv("OPENAI_API_KEY"), "demo")
    }

    fun startConversationWith(assistant: Assistant) {
        val scanner = Scanner(System.`in`)
        while (true) {
            println("==================================================")
            println("User: ")
            val userQuery = scanner.nextLine()
            println("==================================================")

            if ("exit" == userQuery) {
                break
            }

            val agentAnswer = assistant.answer(userQuery)
            println("==================================================")
            println("Assistant: " + agentAnswer)
        }
    }

    fun glob(glob: String): PathMatcher {
        return FileSystems.getDefault().getPathMatcher("glob:" + glob)
    }

    fun toPath(relativePath: String): Path {
        try {
            val fileUrl = Utils::class.java.getClassLoader().getResource(relativePath)
            return Paths.get(fileUrl.toURI())
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }
}
