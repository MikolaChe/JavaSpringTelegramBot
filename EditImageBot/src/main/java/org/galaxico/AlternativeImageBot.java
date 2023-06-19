package org.galaxico;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlternativeImageBot {
    private static final Logger logger = Logger.getLogger(AlternativeImageBot.class.getName());

    public static void main(String[] args) {
        try {
            configureLogger();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            AlterImageBot bot = new AlterImageBot();
            botsApi.registerBot(bot);
            logger.info("Bot is successfully started!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void configureLogger() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.ALL);
                handler.setFormatter(new java.util.logging.SimpleFormatter() {
                    @Override
                    public synchronized String format(java.util.logging.LogRecord record) {
                        return record.getMessage() + System.lineSeparator();
                    }
                });
            }
        }
    }
}

class AlterImageBot extends TelegramLongPollingBot { // the Bot as it is here
    @Override
    public String getBotUsername() {
        return "GalaxicoEditImageBot";
    }

    @Override
    public String getBotToken() {
        return "YOU_BOT_TOKEN";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                String transliteratedText = Transliterator.transliterate(messageText);
                sendTextMessage(transliteratedText, update);
            } else if (update.getMessage().hasPhoto()) {
                PhotoEditor photoHandler = new PhotoEditor(this);
                photoHandler.handlePhoto(update);
            }
        }
    }

    private void sendTextMessage(String text, Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChat().getId());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

class PhotoEditor { // Class for changing photos
    private AlterImageBot bot;

    public PhotoEditor(AlterImageBot bot) {
        this.bot = bot;
    }

    public void handlePhoto(Update update) {
        GetDownloadedToBotImageURL imageURLGetter = new GetDownloadedToBotImageURL(this.bot);
        ImageDownLoader imageDownLoader = new ImageDownLoader();
        File imageFile = imageDownLoader.downloadImage(imageURLGetter.getImageURL(update));

        if (imageFile != null) {
            List<File> filteredImages = ImageFilter.applyFilter(imageFile);
            sendFilteredImages(filteredImages, update);
        }
    }

    private void sendFilteredImages(List<File> filteredImages, Update update) {
        for (File filteredImage : filteredImages) {
            try {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(update.getMessage().getChatId());
                sendPhoto.setPhoto(new InputFile(filteredImage));
                bot.execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

class GetDownloadedToBotImageURL { // getting image-link that user put to chat-bot
    private AlterImageBot bot;
    private static final Logger logger = Logger.getLogger(GetDownloadedToBotImageURL.class.getName());

    public GetDownloadedToBotImageURL(AlterImageBot bot) {
        this.bot = bot;
    }

    public String getImageURL(Update update) {
        String downloadedToBotImageURL = "";
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            PhotoSize largestPhoto = getLargestPhotoSize(update.getMessage().getPhoto());

            GetFile getFile = new GetFile();
            getFile.setFileId(largestPhoto.getFileId());

            try {
                org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFile);
                String fileUrl = "https://api.telegram.org/file/bot" + bot.getBotToken() + "/" + file.getFilePath();
                logger.info("Image link: " + fileUrl);
                downloadedToBotImageURL = fileUrl;

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return downloadedToBotImageURL;
    }

    private PhotoSize getLargestPhotoSize(List<PhotoSize> photoSizes) { // This part is getting for downloading file of the bigest resolution
        PhotoSize largestPhoto = null;
        int maxPhotoSize = 0;

        for (PhotoSize photo : photoSizes) {
            if (photo.getFileSize() > maxPhotoSize) {
                largestPhoto = photo;
                maxPhotoSize = photo.getFileSize();
            }
        }

        return largestPhoto;
    }
}

class ImageDownLoader {
    public File downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String fileExtension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
            String fileName = generateFileName(fileExtension);
            String imageDirectory = "src\\main\\java\\org\\galaxico\\images\\";
            Path targetPath = Path.of(imageDirectory + fileName);

            try (InputStream in = url.openStream()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("Изображение успешно скачано: " + targetPath.toString());

            return targetPath.toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateFileName(String fileExtension) {
        long currentTimeMillis = System.currentTimeMillis();
        return "image" + currentTimeMillis + "." + fileExtension;
    }
}