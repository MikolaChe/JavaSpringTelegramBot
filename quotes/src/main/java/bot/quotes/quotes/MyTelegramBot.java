package bot.quotes.quotes;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String messageText = update.getMessage().getText();
            String quote = getRandomQuote(); // Метод для получения случайной цитаты

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(quote);


            try {
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getRandomQuote() {
        try {
            // подключаем страницу и качаем ее код
            Document document = Jsoup.connect("http://ibash.org.ru/random.php").get();

            // колчаю цитату из кода
            Elements quoteElements = document.select("div.quotbody");
            if (quoteElements.size() > 0) {
                Element quoteElement = quoteElements.get(0);
                String quoteText = quoteElement.text();
                return "Цитата: " + quoteText;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Если ошибка - вернуть пустую строку
        return "";
    }


    @Override
    public String getBotUsername() {
        return "AnyQuotesSilverBot";
    }

    @Override
    public String getBotToken() {
        return "HERE-IS-BOT-TOKEN";
    }
}
