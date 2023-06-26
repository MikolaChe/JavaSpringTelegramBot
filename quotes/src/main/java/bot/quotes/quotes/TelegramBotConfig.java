package bot.quotes.quotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(MyTelegramBot telegramBot) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return telegramBotsApi;
    }

    @Bean
    public MyTelegramBot myTelegramBot() {
        return new MyTelegramBot();
    }


    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }
}

