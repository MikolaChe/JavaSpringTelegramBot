package org.galaxico;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class AnnotationBot {
    public static void main(String[] args) throws Exception {
        System.out.println("Выберите команду");
        Commands commands = new Commands();
        for (Method method : commands.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);
                if (command.showInHelp()) System.out.println(command.name());
            }
        }
        Scanner scanner = new Scanner(System.in);
        final String myCommand = scanner.nextLine();
        for (Method method : commands.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);
                if (command.name().equals(myCommand)) method.invoke(commands);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Command {
        String name();

        boolean showInHelp();
    }

    static class Commands {

        @Command(name = "hello", showInHelp = true)
        public void hello() {
            System.out.println("Hello, human");
        }

        @Command(name = "bui", showInHelp = true)
        public void bui() {
            System.out.println("Bui, human");
        }

        @Command(name = "stopped", showInHelp = false)
        public void stopped() {
            System.out.println("I'm stopped");
        }
    }
}
