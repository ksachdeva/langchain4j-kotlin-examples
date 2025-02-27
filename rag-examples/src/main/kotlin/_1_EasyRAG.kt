import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments
import dev.langchain4j.data.document.splitter.DocumentSplitters
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore

fun createContentRetriever(documents: List<Document>): ContentRetriever {
    val embeddingStore = InMemoryEmbeddingStore<TextSegment>()

    val splitter = DocumentSplitters.recursive(300, 0)
    val embeddingModel = BgeSmallEnV15QuantizedEmbeddingModel()

    for (document in documents) {
        val segments = splitter.split(document)
        val embeddings = embeddingModel.embedAll(segments).content()
        embeddingStore.addAll(embeddings, segments)
    }

    return EmbeddingStoreContentRetriever.from(embeddingStore)
}

fun main() {

    val model =
        OpenAiChatModel.builder()
            .apiKey(Utils.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val documents = loadDocuments(Utils.toPath("documents/"), Utils.glob("*.txt"))

    val retriever = createContentRetriever(documents)

    val assistant =
        AiServices.builder(Assistant::class.java)
            .chatLanguageModel(model)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .contentRetriever(retriever)
            .build()

    Utils.startConversationWith(assistant)
}
