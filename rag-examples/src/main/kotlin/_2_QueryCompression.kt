import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument
import dev.langchain4j.data.document.splitter.DocumentSplitters
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.rag.DefaultRetrievalAugmentor
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore

fun createContentRetriever(document: Document): ContentRetriever {

    val embeddingStore = InMemoryEmbeddingStore<TextSegment>()
    val embeddingModel = BgeSmallEnV15QuantizedEmbeddingModel()

    val ingestor =
        EmbeddingStoreIngestor.builder()
            .documentSplitter(DocumentSplitters.recursive(300, 0))
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .build()

    ingestor.ingest(document)

    val contentRetriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .maxResults(2)
            .minScore(0.6)
            .build()

    return contentRetriever
}

fun main() {

    val model =
        OpenAiChatModel.builder()
            .apiKey(Utils.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val document = loadDocument(Utils.toPath("documents/biography-of-john-doe.txt"))

    val retriever = createContentRetriever(document)

    val queryTransformer = CompressingQueryTransformer(model)

    // The RetrievalAugmentor serves as the entry point into the RAG flow in LangChain4j.
    // It can be configured to customize the RAG behavior according to your requirements.
    // In subsequent examples, we will explore more customizations.
    val retrievalAugmentor =
        DefaultRetrievalAugmentor.builder()
            .queryTransformer(queryTransformer)
            .contentRetriever(retriever)
            .build()

    var assistant =
        AiServices.builder(Assistant::class.java)
            .chatLanguageModel(model)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .retrievalAugmentor(retrievalAugmentor)
            .build()

    Utils.startConversationWith(assistant)
}
