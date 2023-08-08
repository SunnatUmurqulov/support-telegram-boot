package uz.zerone.supporttelegrambot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class SupportTelegramBot(
    private val messageHandler: MessageHandler,
    private val callbackQueryHandler: CallbackQueryHandler,
    private val editedMessageHandler: EditedMessageHandler,
    @Value("\${telegram.bot-username}")
    private val username: String,
    @Value("\${telegram.bot-token}")
    private val token: String,

    ) : TelegramLongPollingBot(token) {

    override fun getBotUsername() = username
    override fun onUpdateReceived(update: Update) {
        when {
            update.hasCallbackQuery() -> callbackQueryHandler.handle(update.callbackQuery, this)
            update.hasMessage() -> messageHandler.handle(update.message, this)
            update.hasEditedMessage() -> editedMessageHandler.handle(update.editedMessage, this)
        }
    }


}


