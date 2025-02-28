# langchain4j examples written using Kotlin

This is a port of https://github.com/langchain4j/langchain4j-examples to Kotlin.

The primary reason of this port is to learn Kotlin language and langchain4j library.

## Setup

The repository uses devcontainer to setup the development environment.

So far I have used vscode with devcontainer extension to setup the development environment and the experience has not been optimal w.r.t kotlin extension support.

The repository uses gradle and has multiple subprojects.

Most examples are using `OPENAI_API_KEY`. Make sure it is present in your environment variable.


## Running the examples

```bash
./gradlew :<project_name>:run -PmainClass=<name_of_main_class>

# example
./gradlew :tutorials:run -PmainClass=_00_HelloWorld
./gradlew --console plain :rag-examples:run -PmainClass=_2_QueryCompression
```

```bash
# for examples that require arguments
./gradlew :<project_name>:run -PmainClass=<name_of_main_class> --args="<arguments>"
# example
./gradlew :tutorials:run -PmainClass=_03_PromptTemplate --args="simple"
```

## About Examples

### Tutorials

- [Simplest chat query](tutorials/src/main/kotlin/_00_HelloWorld.kt)
- [Play with chat model parameters](tutorials/src/main/kotlin/_01_ModelParameters.kt)
- [Generate an image using DALL-E](tutorials/src/main/kotlin/_02_OpenAiImageModelExamples.kt)
- [Simple & Structured Prompt templating](tutorials/src/main/kotlin/_03_PromptTemplate.kt)
- [How to stream response](tutorials/src/main/kotlin/_04_Streaming.kt)
- [Chat context memory](tutorials/src/main/kotlin/_05_Memory.kt)
- [How to use examples in context (Few Shot)](tutorials/src/main/kotlin/_06_FewShot.kt)
- [Hello AiServices](tutorials/src/main/kotlin/_08_AIServiceEx1.kt)
- [[AiService] Using SystemMessage in interface](tutorials/src/main/kotlin/_08_AIServiceEx2.kt)
- [[AiService] Using SystemMessage & UserMessage in interface](tutorials/src/main/kotlin/_08_AIServiceEx3.kt)
- [[AiService] StructuredOutput - A simple example](tutorials/src/main/kotlin/_08_AIServiceEx4.kt)
- [[AiService] StructuredOutput - A bit more](tutorials/src/main/kotlin/_08_AIServiceEx5.kt)
- [[AiService] StructuredOutput - More types](tutorials/src/main/kotlin/_08_AIServiceEx6.kt)
- [[AiService] Include ChatMemory](tutorials/src/main/kotlin/_08_AIServiceEx7.kt)
- [[AiService] Include ChatMemory for every user](tutorials/src/main/kotlin/_08_AIServiceEx8.kt)

## RAG Examples

- [Simple RAG](rag-examples/src/main/kotlin/_1_EasyRAG.kt)
- [Query Compression](rag-examples/src/main/kotlin/_2_QueryCompression.kt)
