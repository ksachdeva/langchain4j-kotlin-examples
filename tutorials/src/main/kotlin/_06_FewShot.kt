import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler
import dev.langchain4j.model.openai.OpenAiChatModelName
import dev.langchain4j.model.openai.OpenAiStreamingChatModel

fun main() {

    val model =
        OpenAiStreamingChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(OpenAiChatModelName.GPT_4_O_MINI)
            .build()

    val fewShotHistory = mutableListOf<ChatMessage>()

    // Adding positive feedback example to history
    fewShotHistory.add(
        UserMessage.from(
            "I love the new update! The interface is very user-friendly and the new features are amazing!"
        )
    )

    fewShotHistory.add(
        AiMessage.from(
            "Action: forward input to positive feedback storage\nReply: Thank you very much for this great feedback! We have transmitted your message to our product development team who will surely be very happy to hear this. We hope you continue enjoying using our product."
        )
    )

    // Adding negative feedback example to history
    fewShotHistory.add(
        UserMessage.from("I am facing frequent crashes after the new update on my Android device.")
    )
    fewShotHistory.add(
        AiMessage.from(
            "Action: open new ticket - crash after update Android\nReply: We are so sorry to hear about the issues you are facing. We have reported the problem to our development team and will make sure this issue is addressed as fast as possible. We will send you an email when the fix is done, and we are always at your service for any further assistance you may need."
        )
    )

    // Adding another positive feedback example to history
    fewShotHistory.add(
        UserMessage.from("Your app has made my daily tasks so much easier! Kudos to the team!")
    )
    fewShotHistory.add(
        AiMessage.from(
            "Action: forward input to positive feedback storage\nReply: Thank you so much for your kind words! We are thrilled to hear that our app is making your daily tasks easier. Your feedback has been shared with our team. We hope you continue to enjoy using our app!"
        )
    )

    // Adding another negative feedback example to history
    fewShotHistory.add(
        UserMessage.from("The new feature is not working as expected. It’s causing data loss.")
    )
    fewShotHistory.add(
        AiMessage.from(
            "Action: open new ticket - data loss by new feature\nReply:We apologize for the inconvenience caused. Your feedback is crucial to us, and we have reported this issue to our technical team. They are working on it on priority. We will keep you updated on the progress and notify you once the issue is resolved. Thank you for your patience and support."
        )
    )

    val customerComplaint =
        UserMessage.from("How can your app be so slow? Please do something about it!")
    fewShotHistory.add(customerComplaint)

    val responseHandler =
        object : StreamingChatResponseHandler {
            override fun onPartialResponse(message: String) {
                println("Received message: $message")
            }

            override fun onCompleteResponse(message: ChatResponse) {
                println("\n\nDone streaming")
            }

            override fun onError(throwable: Throwable) {
                println("Error: ${throwable.message}")
            }
        }

    model.chat(fewShotHistory, responseHandler)
}
